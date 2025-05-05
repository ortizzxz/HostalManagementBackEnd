package com.hostalmanagement.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.hostalmanagement.app.DTO.GuestDTO;
import com.hostalmanagement.app.DTO.GuestReservationDTO;
import com.hostalmanagement.app.DTO.ReservationDTO;
import com.hostalmanagement.app.DTO.TenantDTO;
import com.hostalmanagement.app.dao.ReservationDAO;
import com.hostalmanagement.app.dao.RoomDAO;
import com.hostalmanagement.app.model.Guest;
import com.hostalmanagement.app.model.GuestReservation;
import com.hostalmanagement.app.model.Reservation;
import com.hostalmanagement.app.model.Reservation.ReservationState;
import com.hostalmanagement.app.model.Room;
import com.hostalmanagement.app.model.Tenant;

@Service
public class ReservationService {

    @Autowired
    ReservationDAO reservationDAO;

    @Autowired
    RoomDAO roomDAO;

    @Autowired
    TenantService tenantService;

    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        try {
            // Step 1: Validate the input DTO if needed
            if (reservationDTO == null) {
                throw new IllegalArgumentException("Reservation data cannot be null");
            }

            // Step 2: Convert the DTO to the Reservation entity
            Reservation reservation = toEntity(reservationDTO);

            // Step 3: Persist the entity to the database
            reservationDAO.save(reservation);

            // Step 4: Convert the entity back to DTO and return it
            return toDTO(reservation);
        } catch (IllegalArgumentException e) {
            // Catch any invalid arguments (e.g., missing or incorrect data in
            // reservationDTO)
            throw new IllegalArgumentException("Reservation couldn't be created: " + e.getMessage(), e);
        } catch (DataAccessException e) {
            // Handle any database-related exceptions (e.g., problems during save)
            throw new RuntimeException("Database error while creating reservation: " + e.getMessage(), e);
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            throw new RuntimeException("Unexpected error occurred while creating reservation: " + e.getMessage(), e);
        }
    }

    // Devuelve todos las reservas
    public List<ReservationDTO> findAllReservations(Tenant tenant) {
        List<Reservation> reservations = reservationDAO.findAll(tenant);
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

    public ReservationDTO toDTO(Reservation reservation) {
        List<GuestDTO> guestDTOs = reservation.getGuests()
                .stream()
                .map(gr -> {
                    Guest g = gr.getGuest();
                    TenantDTO tenant = tenantService.toDTO(g.getTenant());
                    return new GuestDTO(
                            g.getNIF(),
                            g.getName(),
                            g.getLastname(),
                            g.getEmail(),
                            g.getPhone(),
                            g.getTenant().getId());
                })
                .toList();

        return new ReservationDTO(
                reservation.getId(),
                reservation.getRoom().getId(),
                reservation.getInDate(),
                reservation.getOutDate(),
                reservation.getState().toString(),
                guestDTOs,
                reservation.getRoom().getTenant().getId());
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
                            guestReservationDTO.getPhone(),
                            room.getTenant());

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
