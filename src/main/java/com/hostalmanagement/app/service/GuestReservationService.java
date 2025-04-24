package com.hostalmanagement.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostalmanagement.app.DTO.GuestDTO;
import com.hostalmanagement.app.DTO.GuestReservationDTO;
import com.hostalmanagement.app.DTO.ReservationDTO;
import com.hostalmanagement.app.dao.GuestDAO;
import com.hostalmanagement.app.dao.GuestReservationDAO;
import com.hostalmanagement.app.dao.ReservationDAO;
import com.hostalmanagement.app.dao.RoomDAO;
import com.hostalmanagement.app.model.Guest;
import com.hostalmanagement.app.model.GuestReservation;
import com.hostalmanagement.app.model.Reservation;
import com.hostalmanagement.app.model.Reservation.ReservationState;
import com.hostalmanagement.app.model.Room;

import jakarta.transaction.Transactional;

@Service
public class GuestReservationService {

    @Autowired
    private GuestReservationDAO guestReservationDAO;

    @Autowired
    private GuestDAO guestDAO;

    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private RoomDAO roomDAO;

    @Transactional
    public Reservation createGuestReservation(GuestReservationDTO guestReservationDTO) {
        // Default value for ReservationState
        ReservationState reservationState;
        // Convert from String to ENUM
        try {
            reservationState = ReservationState
                    .valueOf(guestReservationDTO.getReservationDTO().getState().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException(
                    "Invalid reservation state: " + guestReservationDTO.getReservationDTO().getState());
        }

        // Find the Room (assuming the Room object is already created)
        Room room = roomDAO.findById(guestReservationDTO.getReservationDTO().getRoomId());

        // Create a list of GuestReservations based on guests in the GuestReservationDTO
        List<GuestReservation> guestReservations = new ArrayList<>();
        for (GuestDTO guestDTO : guestReservationDTO.getReservationDTO().getGuests()) {
            // Ensure the guest exists or create a new one if necessary
            Guest guest = guestDAO.findByNIF(guestDTO.getNif());
            if (guest == null) {
                guest = new Guest(guestDTO.getEmail(), guestDTO.getLastname(), guestDTO.getName(), guestDTO.getNif(),
                        guestDTO.getPhone());
                guestDAO.save(guest);
            }

            // Create GuestReservation for each guest
            GuestReservation guestReservation = new GuestReservation(guest, null); // Reservation is set later
            guestReservations.add(guestReservation);
        }

        // Create the Reservation object
        Reservation reservation = new Reservation(room, guestReservationDTO.getReservationDTO().getInDate(),
                guestReservationDTO.getReservationDTO().getOutDate(), reservationState, guestReservations);
        reservationDAO.save(reservation);

        // Set the reservation for each GuestReservation and save them
        for (GuestReservation guestReservation : guestReservations) {
            guestReservation.setReservation(reservation); // Set the reservation after creation
            guestReservationDAO.save(guestReservation);
        }

        return reservation; // Return the saved reservation
    }

    public GuestReservationDTO findGuestReservaitonByReservationId(Long Id) {
        GuestReservation guestReservation = guestReservationDAO.findByReservation(Id);
        return (guestReservation != null) ? toDTO(guestReservation) : null;
    }

    public GuestReservationDTO findGuestReservaitonByGuestNIF(String NIF) {
        GuestReservation guestReservation = guestReservationDAO.findByNIF(NIF);
        return (guestReservation != null) ? toDTO(guestReservation) : null;
    }

    public List<GuestReservationDTO> findAllGuestReservations() {
        List<GuestReservation> guestReservations = guestReservationDAO.findAll();
        return guestReservations.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private GuestReservationDTO toDTO(GuestReservation guestReservation) {
        // Convert the Guest entity to a GuestDTO
        GuestDTO guestDTO = guestDAO.toGuestDTO(guestReservation.getGuest());

        // Create a list containing the single GuestDTO, as GuestReservationDTO expects
        // a List<GuestDTO>
        List<GuestDTO> guestDTOList = new ArrayList<>();
        guestDTOList.add(guestDTO);

        // Convert the Reservation entity to a ReservationDTO
        ReservationDTO reservationDTO = reservationDAO.toReservationDTO(guestReservation.getReservation());

        // Return the GuestReservationDTO with the list of GuestDTO and the
        // ReservationDTO
        return new GuestReservationDTO(guestDTOList, reservationDTO);
    }

    private GuestReservation toEntity(GuestReservationDTO guestReservationDTO) {
        // Assuming guestDTO is a list of GuestDTOs, we'll retrieve the first guest from the list
        GuestDTO guestDTO = guestReservationDTO.getGuestDTO().get(0); // Assuming there's always at least one guest
        
        // Find the Guest entity by NIF
        Guest guest = guestDAO.findByNIF(guestDTO.getNif());
        if (guest == null) {
            guest = new Guest(guestDTO.getEmail(), guestDTO.getLastname(), guestDTO.getName(), guestDTO.getNif(),
                        guestDTO.getPhone());
            throw new IllegalArgumentException("Guest not found with NIF: " + guestDTO.getNif());
        }
        
        // Find the Reservation entity by the ID
        ReservationDTO reservationDTO = guestReservationDTO.getReservationDTO();
        Reservation reservation = reservationDAO.findById(reservationDTO.getRoomId());
        if (reservation == null) {
            // Handle the case where the reservation does not exist, e.g., throw an exception
            throw new IllegalArgumentException("Reservation not found with ID: " + reservationDTO.getRoomId());
        }
        
        // Now create and return the GuestReservation entity
        return new GuestReservation(guest, reservation);
    }
    
    // public GuestReservationDTO updateGuestReservation(String NIF, GuestDTO
    // guestDTO, ReservationDTO reservationDTO) {
    // GuestReservation existingGuestReservation =
    // guestReservationDAO.findByNIF(NIF);

    // if (existingGuestReservation != null) {
    // // Update Guest entity
    // Guest guest = existingGuestReservation.getGuest();
    // guest.setName(guestDTO.getName());
    // guest.setLastname(guestDTO.getLastname());
    // guest.setEmail(guestDTO.getEmail());
    // guest.setPhone(guestDTO.getPhone());
    // guestDAO.save(guest);

    // // Optional: Update Reservation entity (or reassign it)
    // Reservation reservation = existingGuestReservation.getReservation();
    // reservation.setInDate(reservationDTO.getInDate());
    // reservation.setOutDate(reservationDTO.getOutDate());
    // reservation.setStatus(Reservation.ReservationState.valueOf(reservationDTO.getState()));
    // reservationDAO.save(reservation);

    // // Save GuestReservation (if needed)
    // guestReservationDAO.save(existingGuestReservation);

    // // Return updated DTO
    // return new GuestReservationDTO(guestDTO, reservationDTO);
    // }

    // return null; // or throw an exception if not found
    // }

    // TODO: Logic
    public boolean deleteGuestReservation(String NIF) {
        return true;
    }

}
