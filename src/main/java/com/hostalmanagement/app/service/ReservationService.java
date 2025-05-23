package com.hostalmanagement.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.hostalmanagement.app.DTO.GuestDTO;
import com.hostalmanagement.app.DTO.ReservationDTO;
import com.hostalmanagement.app.dao.GuestDAO;
import com.hostalmanagement.app.dao.ReservationDAO;
import com.hostalmanagement.app.dao.RoomDAO;
import com.hostalmanagement.app.model.Guest;
import com.hostalmanagement.app.model.GuestReservation;
import com.hostalmanagement.app.model.Reservation;
import com.hostalmanagement.app.model.Reservation.ReservationState;
import com.hostalmanagement.app.model.Room;
import com.hostalmanagement.app.model.Tenant;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReservationService {

    @Autowired
    ReservationDAO reservationDAO;

    @Autowired
    RoomDAO roomDAO;

    @Autowired
    GuestDAO guestDAO;

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
            // === Update core reservation fields ===
            existingReservation.setInDate(reservationDTO.getInDate());
            existingReservation.setOutDate(reservationDTO.getOutDate());

            try {
                ReservationState newState = ReservationState.valueOf(reservationDTO.getState());
                existingReservation.setStatus(newState);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid reservation state: " + reservationDTO.getState());
            }

            existingReservation.setRoom(roomDAO.findById(reservationDTO.getRoomId()));

            // === Update guests ===
            List<GuestDTO> incomingGuestDTOs = reservationDTO.getGuests();
            List<GuestReservation> updatedGuestReservations = new ArrayList<>();

            for (GuestDTO guestDTO : incomingGuestDTOs) {
                Optional<Guest> optionalGuest = guestDAO.findByNIF(guestDTO.getNif());
                Guest guest;

                if (optionalGuest.isPresent()) {
                    guest = optionalGuest.get();
                } else {
                    guest = new Guest();
                    guest.setNif(guestDTO.getNif());
                    guest.setTenant(tenantService.findById(guestDTO.getTenantId()));
                }

                guest.setName(guestDTO.getName());
                guest.setLastname(guestDTO.getLastname());
                guest.setEmail(guestDTO.getEmail());
                guest.setPhone(guestDTO.getPhone());

                guestDAO.save(guest);

                // Create GuestReservation linking guest and reservation
                GuestReservation guestReservation = new GuestReservation();
                guestReservation.setGuest(guest);
                guestReservation.setReservation(existingReservation);

                updatedGuestReservations.add(guestReservation);
            }

            // Clear and set new guests
            existingReservation.getGuests().clear();
            existingReservation.getGuests().addAll(updatedGuestReservations);

            reservationDAO.update(existingReservation);
            return toDTO(existingReservation);

        }

        return null;
    }

    public ReservationDTO updateStatus(Long id, ReservationDTO reservationDTO) {
        Reservation existingReservation = reservationDAO.findById(id);
        if (existingReservation != null) {
            try {
                // Safely parse and update the status
                ReservationState newState = ReservationState.valueOf(reservationDTO.getState());
                existingReservation.setStatus(newState);
                reservationDAO.update(existingReservation);

                // Return the updated DTO
                return toDTO(existingReservation);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid reservation status: " + reservationDTO.getState());
            }
        } else {
            throw new EntityNotFoundException("Reservation with ID " + id + " not found");
        }
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

                    // create a new GuestReservation using the guest and the current reservation
                    return new GuestReservation(guest, reseservation);
                })
                .collect(Collectors.toList());

        // Finally, create and return the Reservation entity
        return new Reservation(room, reservationDTO.getInDate(), reservationDTO.getOutDate(), reservationState,
                guestReservations);
    }

}
