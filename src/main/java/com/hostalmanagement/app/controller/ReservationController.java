package com.hostalmanagement.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hostalmanagement.app.HostalManagementApplication;
import com.hostalmanagement.app.DTO.GuestDTO;
import com.hostalmanagement.app.DTO.GuestReservationDTO;
import com.hostalmanagement.app.DTO.ReservationDTO;
import com.hostalmanagement.app.config.SecurityConfig;
import com.hostalmanagement.app.service.GuestReservationService;
import com.hostalmanagement.app.service.GuestService;
import com.hostalmanagement.app.service.ReservationService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final SecurityConfig securityConfig;

    private final HostalManagementApplication hostalManagementApplication;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private GuestService guestService;
    
    @Autowired
    private GuestReservationService guestReservationService;

    ReservationController(HostalManagementApplication hostalManagementApplication, SecurityConfig securityConfig) {
        this.hostalManagementApplication = hostalManagementApplication;
        this.securityConfig = securityConfig;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createReservation(@RequestBody ReservationDTO request) {
        // Create the reservation and save it
        ReservationDTO reservationDTO = new ReservationDTO(request.getRoomId(), request.getInDate(), request.getOutDate(), request.getState(), request.getGuests());
        
        // Call the service method to create the reservation and guest reservations
        guestReservationService.createGuestReservation(new GuestReservationDTO(request.getGuests(), reservationDTO));

        return ResponseEntity.ok("Reservation created successfully");
    }

}
