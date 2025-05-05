package com.hostalmanagement.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostalmanagement.app.DTO.GuestDTO;
import com.hostalmanagement.app.DTO.GuestReservationDTO;
import com.hostalmanagement.app.DTO.ReservationDTO;
import com.hostalmanagement.app.dao.CheckInOutDAO;
import com.hostalmanagement.app.dao.GuestDAO;
import com.hostalmanagement.app.dao.GuestReservationDAO;
import com.hostalmanagement.app.dao.ReservationDAO;
import com.hostalmanagement.app.dao.ReservationJpaDAO;
import com.hostalmanagement.app.dao.RoomDAO;
import com.hostalmanagement.app.model.CheckInOut;
import com.hostalmanagement.app.model.Guest;
import com.hostalmanagement.app.model.GuestReservation;
import com.hostalmanagement.app.model.Reservation;
import com.hostalmanagement.app.model.Reservation.ReservationState;
import com.hostalmanagement.app.model.Room;
import com.hostalmanagement.app.model.Tenant;

import jakarta.transaction.Transactional;

@Service
public class GuestReservationService {

    @Autowired
    private TenantService tenantService;

    @Autowired
    private GuestReservationDAO guestReservationDAO;

    @Autowired
    private GuestDAO guestDAO;

    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private RoomDAO roomDAO;

    @Autowired
    private ReservationJpaDAO reservationJpaDAO;

    @Autowired
    private CheckInOutDAO checkInOutDAO;

    private Reservation createReservationEntity(GuestReservationDTO dto) {
        ReservationDTO reservationDTO = dto.getReservationDTO();
        Room room = roomDAO.findById(reservationDTO.getRoomId());

        return new Reservation(
                room,
                reservationDTO.getInDate(),
                reservationDTO.getOutDate(),
                ReservationState.valueOf(reservationDTO.getState().toUpperCase()),
                new ArrayList<>());
    }

    private Guest getOrCreateGuest(GuestDTO guestDTO) {
        Guest guest = guestDAO.findByNIF(guestDTO.getNif());
        Tenant tenant = tenantService.findById(guestDTO.getTenantId());
        if (guest == null) {
            guest = new Guest(
                    guestDTO.getEmail(),
                    guestDTO.getLastname(),
                    guestDTO.getName(),
                    guestDTO.getNif(),
                    guestDTO.getPhone(),
                    tenant);
            guestDAO.save(guest);
        }
        return guest;
    }

    @Transactional
    public Reservation createGuestReservation(GuestReservationDTO dto) {
        // Step 1: Create and save the Reservation entity
        Reservation reservation = createReservationEntity(dto);
        reservation = reservationJpaDAO.saveAndFlush(reservation); // Save and flush the reservation to get the saved
                                                                   // entity

        final Reservation savedReservation = reservation; // Now this is effectively final to be used in lambda
                                                          // expressions

        // Step 2: Iterate over the guests and create Guest entities, linking them with
        // the reservation
        dto.getReservationDTO().getGuests().forEach(guestDTO -> {
            // Step 2.1: Get or create a guest based on the provided guest data
            Guest guest = getOrCreateGuest(guestDTO);

            // Step 2.2: Save the GuestReservation which links the guest with the
            // reservation
            guestReservationDAO.save(new GuestReservation(guest, savedReservation));
        });

        // Step 3: Create Check-in and Check-out records
        createCheckInOut(savedReservation);

        // Step 4: Return the saved reservation
        return savedReservation;
    }

    // Method to create check-in and check-out entries for the reservation
    private void createCheckInOut(Reservation reservation) {
        // Create check-in entry
        CheckInOut checkInOut = new CheckInOut();
        checkInOut.setReservation(reservation);
        checkInOut.setInDate(reservation.getInDate()); // The check-in date is the inDate of the reservation
        checkInOut.setOutDate(reservation.getOutDate()); // The check-out date is the outDate of the reservation

        // Save the check-in/out details
        checkInOutDAO.save(checkInOut);
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
        // Assuming guestDTO is a list of GuestDTOs, we'll retrieve the first guest from
        // the list
        GuestDTO guestDTO = guestReservationDTO.getGuestDTO().get(0); // Assuming there's always at least one guest

        // Find the Guest entity by NIF
        Guest guest = guestDAO.findByNIF(guestDTO.getNif());
        Tenant tenant = tenantService.findById(guestDTO.getTenantId());

        if (guest == null) {
            guest = new Guest(guestDTO.getEmail(), guestDTO.getLastname(), guestDTO.getName(), guestDTO.getNif(),
                    guestDTO.getPhone(), tenant);
            throw new IllegalArgumentException("Guest not found with NIF: " + guestDTO.getNif());
        }

        // Find the Reservation entity by the ID
        ReservationDTO reservationDTO = guestReservationDTO.getReservationDTO();
        Reservation reservation = reservationDAO.findById(reservationDTO.getRoomId());
        if (reservation == null) {
            // Handle the case where the reservation does not exist, e.g., throw an
            // exception
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
