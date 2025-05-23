package com.hostalmanagement.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hostalmanagement.app.HostalManagementApplication;
import com.hostalmanagement.app.DTO.CheckInOutDTO;
import com.hostalmanagement.app.DTO.GuestReservationDTO;
import com.hostalmanagement.app.DTO.ReservationDTO;
import com.hostalmanagement.app.config.SecurityConfig;
import com.hostalmanagement.app.model.Tenant;
import com.hostalmanagement.app.service.CheckInOutService;
import com.hostalmanagement.app.service.GuestReservationService;
import com.hostalmanagement.app.service.ReservationService;
import com.hostalmanagement.app.service.TenantService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final SecurityConfig securityConfig;

    private final HostalManagementApplication hostalManagementApplication;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private GuestReservationService guestReservationService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private CheckInOutService checkInOutService;

    ReservationController(HostalManagementApplication hostalManagementApplication, SecurityConfig securityConfig) {
        this.hostalManagementApplication = hostalManagementApplication;
        this.securityConfig = securityConfig;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> findAllReservations(Long tenantId) {
        Tenant tenant = tenantService.findById(tenantId);
        List<ReservationDTO> reservations = reservationService.findAllReservations(tenant);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/checkins")
    public ResponseEntity<List<CheckInOutDTO>> getAllCheckIns(Long tenantId) {
        List<CheckInOutDTO> checkins = checkInOutService.findAllCheckInOuts(tenantId);

        return ResponseEntity.ok(checkins);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createReservation(@RequestBody ReservationDTO request) {
        // Create the reservation and save it
        ReservationDTO reservationDTO = new ReservationDTO(null, request.getRoomId(), request.getInDate(),
                request.getOutDate(), request.getState(), request.getGuests(), request.getTenantId());

        // Call the service method to create the reservation and guest reservations
        guestReservationService.createGuestReservation(new GuestReservationDTO(request.getGuests(), reservationDTO));

        return ResponseEntity.ok("Reservation created successfully");
    }

    @PostMapping("/checkins/update")
    @Transactional
    public ResponseEntity<String> updateCheckIn(@RequestBody CheckInOutDTO checkInOutDTO) {
        checkInOutService.updateCheckIn(checkInOutDTO);
        return ResponseEntity.ok("Check-in updated successfully");
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateReservation(
            @PathVariable Long id,
            @RequestBody ReservationDTO request) {

        // Assuming your service has an update method that handles update logic:
        ReservationDTO updated = reservationService.updateReservation(id, request);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Reservation updated successfully");
    }
    @PutMapping("/status/{id}")
    @Transactional
    public ResponseEntity<ReservationDTO> updateState(@PathVariable Long id, @RequestBody ReservationDTO request) {
        ReservationDTO updated = reservationService.updateStatus(id, request);
        if (updated != null) {
            return ResponseEntity.ok(updated); // Return the updated DTO object
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
