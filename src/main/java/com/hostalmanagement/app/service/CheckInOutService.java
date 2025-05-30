package com.hostalmanagement.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostalmanagement.app.DTO.CheckInOutDTO;
import com.hostalmanagement.app.DTO.ReservationDTO;
import com.hostalmanagement.app.dao.CheckInOutDAO;
import com.hostalmanagement.app.model.CheckInOut;

import jakarta.persistence.EntityNotFoundException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CheckInOutService {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private CheckInOutDAO checkInOutDAO;

    public List<CheckInOutDTO> findAllCheckInOuts(Long tenantId) {
        // List<Guest> guests = guestDAO.findAll();
        List<CheckInOut> checkins = checkInOutDAO.findAll(tenantId);
        return checkins.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private CheckInOutDTO toDTO(CheckInOut checkInOut) {
        ReservationDTO reservationDTO = reservationService.toDTO(checkInOut.getReservation());
        return new CheckInOutDTO(
                checkInOut.getId(),
                reservationDTO,
                checkInOut.getInDate(),
                checkInOut.getOutDate());
    }

    public void updateCheckIn(CheckInOutDTO dto) {
        Optional<CheckInOut> optional = checkInOutDAO.findByReservation(dto.getId());
        validateCheckInOutDates(dto.getInDate(), dto.getOutTime());

        if (optional.isPresent()) {
            CheckInOut checkInOut = optional.get();

            // Update check-in/out entity
            checkInOut.setInDate(dto.getInDate());
            checkInOut.setOutDate(dto.getOutTime());
            checkInOutDAO.save(checkInOut);

            // Also update the reservation entity
            ReservationDTO reservationOpt = reservationService.findReservationById(dto.getId());
            if (reservationOpt != null) {
                reservationOpt.setInDate(dto.getInDate());
                reservationOpt.setOutDate(dto.getOutTime());
                reservationService.updateReservation(dto.getId(), reservationOpt);
            } else {
                throw new EntityNotFoundException("Reservation with id " + dto.getId() + " not found");
            }

        } else {
            throw new EntityNotFoundException("CheckInOut with reservation id " + dto.getId() + " not found");
        }
    }

    private void validateCheckInOutDates(LocalDateTime inDate, LocalDateTime outDate) {
        LocalDateTime now = LocalDateTime.now();

        if (inDate.isBefore(now)) {
            throw new IllegalArgumentException("Check-in date cannot be in the past.");
        }

        if (inDate.isAfter(outDate)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date.");
        }

        Duration duration = Duration.between(inDate, outDate);
        if (duration.toHours() < 18) {
            throw new IllegalArgumentException("The minimum reservation duration must be 18 hours.");
        }
    }

}
