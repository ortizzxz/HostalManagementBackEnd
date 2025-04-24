package com.hostalmanagement.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.hostalmanagement.app.DTO.GuestDTO;
import com.hostalmanagement.app.DTO.GuestReservationDTO;
import com.hostalmanagement.app.DTO.ReservationDTO;
import com.hostalmanagement.app.dao.ReservationDAO;
import com.hostalmanagement.app.dao.RoomDAO;
import com.hostalmanagement.app.model.Guest;
import com.hostalmanagement.app.model.GuestReservation;
import com.hostalmanagement.app.model.Reservation;
import com.hostalmanagement.app.model.Reservation.ReservationState;
import com.hostalmanagement.app.model.Room;

@Service
public class ReservationService {

    @Autowired
    ReservationDAO reservationDAO;

    @Autowired
    RoomDAO roomDAO;

    // Registrar una reserva
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        try {
            Reservation reservation = toEntity(reservationDTO);
            reservationDAO.save(reservation);
            return toDTO(reservation);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Reservation couldn't be created" + e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException("Reservation couldn't be created" + e.getMessage());
        }
    }

    // Devuelve todos las reservas
    public List<ReservationDTO> findAllReservations() {
        List<Reservation> reservations = reservationDAO.findAll();
        return reservations.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Buscar reserva por ID
    public ReservationDTO findReservationById(@PathVariable Long id) {
        Reservation reservation = reservationDAO.findById(id);
        return (reservation != null) ? toDTO(reservation) : null;
    }

    // Actualizar una reserva
    public ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO) {
        Reservation existingReservation = reservationDAO.findById(id);
        if (existingReservation != null) {
            // Logic to be applied

            return toDTO(existingReservation);
        }
        return null;
    }

    public boolean deleteReservation(Long id) {
        Reservation reservation = reservationDAO.findById(id);
        if (reservation != null) {
            reservationDAO.delete(id);
            return true;
        }
        return false;

    }

    private ReservationDTO toDTO(Reservation reservation) {
        List<GuestDTO> guestDTOs = reservation.getGuests()
                .stream()
                .map(gr -> {
                    Guest g = gr.getGuest();
                    return new GuestDTO(
                            g.getNIF(),
                            g.getName(),
                            g.getLastname(),
                            g.getEmail(),
                            g.getPhone());
                })
                .toList();

        return new ReservationDTO(
                reservation.getRoom().getId(),
                reservation.getInDate(),
                reservation.getOutDate(),
                reservation.getState().toString(),
                guestDTOs);
    }

    private Reservation toEntity(ReservationDTO reservationDTO) {
        // Default value
        ReservationState reservationState;
        // Convert from String to ENUM
        try {
            reservationState = ReservationState.valueOf(reservationDTO.getState().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid reservation state: " + reservationDTO.getState());
        }

        // Fetch the room (you might already have this handled)
        Room room = roomDAO.findById(reservationDTO.getRoomId());
        
        // ReservationDTO To Entity
        Reservation reseservation = reservationDAO.findById(reservationDTO.getRoomId());

        // Convert the list of GuestReservationDTOs to GuestReservations
        List<GuestReservation> guestReservations = reservationDTO.getGuests().stream()
                .map(guestReservationDTO -> {
                    // Convert the GuestDTO to a Guest entity
                    Guest guest = new Guest(
                            guestReservationDTO.getEmail(),
                            guestReservationDTO.getLastname(),
                            guestReservationDTO.getName(),
                            guestReservationDTO.getNif(),
                            guestReservationDTO.getPhone());

                    // Now create a new GuestReservation using the guest and the current reservation
                    return new GuestReservation(guest, reseservation); // Make sure the reservation is correctly
                                                                        // assigned
                })
                .collect(Collectors.toList());

        // Finally, create and return the Reservation entity
        return new Reservation(room, reservationDTO.getInDate(), reservationDTO.getOutDate(), reservationState,
                guestReservations);
    }

}
