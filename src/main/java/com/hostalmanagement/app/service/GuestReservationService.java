package com.hostalmanagement.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    PDFReportService pdfReportService;

    @Autowired
    EmailService emailService;

    private Reservation createReservationEntity(GuestReservationDTO dto) {
        ReservationDTO reservationDTO = dto.getReservationDTO();
        Room room = roomDAO.findById(reservationDTO.getRoomId());

        Reservation existingReservation = reservationDAO.findExistenceBetweenDate(
                room.getId(),
                room.getTenant().getId(),
                reservationDTO.getInDate(),
                reservationDTO.getOutDate());

        if (existingReservation != null) {
            throw new IllegalStateException("Room is already reserved for the selected dates.");
        }

        return new Reservation(
                room,
                reservationDTO.getInDate(),
                reservationDTO.getOutDate(),
                ReservationState.valueOf(reservationDTO.getState().toUpperCase()),
                new ArrayList<>());
    }

    private Guest getOrCreateGuest(GuestDTO guestDTO) {
        // Step 1: Attempt to find the guest by NIF
        Optional<Guest> existingGuestOpt = guestDAO.findByNIF(guestDTO.getNif());

        // Step 2: If the guest exists, return it
        if (existingGuestOpt.isPresent()) {
            return existingGuestOpt.get();
        }

        // Step 3: Validate that the tenant ID is present
        if (guestDTO.getTenantId() == null) {
            throw new IllegalArgumentException("Tenant ID must not be null for guest: " + guestDTO.getNif());
        }

        // Step 4: Load and validate the tenant
        Tenant tenant = tenantService.findById(guestDTO.getTenantId());
        if (tenant == null) {
            throw new IllegalArgumentException("Tenant not found with ID: " + guestDTO.getTenantId());
        }

        // Step 5: Create and save new Guest
        Guest newGuest = new Guest(
                guestDTO.getEmail(),
                guestDTO.getLastname(),
                guestDTO.getName(),
                guestDTO.getNif(),
                guestDTO.getPhone(),
                tenant // Valid managed Tenant
        );

        return guestDAO.save(newGuest);
    }

    @Transactional
    public Reservation createGuestReservation(GuestReservationDTO dto) {

        if (dto.getReservationDTO().getInDate().isAfter(dto.getReservationDTO().getOutDate())) {
            throw new IllegalArgumentException("Out Date cannot be earlier than In Date");
        }

        // Step 1: Create reservation (includes conflict check)
        Reservation reservation = createReservationEntity(dto);
        reservation = reservationJpaDAO.saveAndFlush(reservation);

        // Step 2: Link guests to reservation
        final Reservation savedReservation = reservation;
        dto.getReservationDTO().getGuests().forEach(guestDTO -> {
            Guest guest = getOrCreateGuest(guestDTO);
            guestReservationDAO.save(new GuestReservation(guest, savedReservation));
        });

        // Step 3: Check-in/out records
        createCheckInOut(savedReservation);

        // Step 4: mainventoryController
        byte[] bill = pdfReportService.generateReservationBill(savedReservation);
        String guestEmail = dto.getReservationDTO().getGuests().get(0).getEmail(); // Assuming at least one guest
        emailService.sendEmailWithAttachment(
                guestEmail,
                "Su factura de Reserva",
                "Gracias por su reserva. Debajo encontrará su factura.",
                bill,
                "reservation_bill.pdf");

        // Step 5: Return saved entity
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
        Optional<Guest> guestOpt = guestDAO.findByNIF(guestDTO.getNif());
        Tenant tenant = tenantService.findById(guestDTO.getTenantId());

        // Check if the guest is present, otherwise create a new one
        Guest guest;
        if (guestOpt.isPresent()) {
            guest = guestOpt.get();
        } else {
            // If guest not found, create a new one and persist it
            guest = new Guest(guestDTO.getEmail(), guestDTO.getLastname(), guestDTO.getName(), guestDTO.getNif(),
                    guestDTO.getPhone(), tenant);
            guestDAO.save(guest); // Save the new guest in the database
        }

        // Find the Reservation entity by the Room ID (assuming this returns Reservation
        // or null)
        ReservationDTO reservationDTO = guestReservationDTO.getReservationDTO();
        Reservation reservation = reservationDAO.findById(reservationDTO.getRoomId()); // Returns Reservation or null

        if (reservation != null) {
            // Return the new GuestReservation entity
            return new GuestReservation(guest, reservation);
        } else {
            // Handle the case where the reservation does not exist
            throw new IllegalArgumentException("Reservation not found with ID: " + reservationDTO.getRoomId());
        }
    }

    // TODO: Logic
    public boolean deleteGuestReservation(String NIF) {
        return true;
    }

}
