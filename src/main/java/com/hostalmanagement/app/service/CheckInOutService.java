package com.hostalmanagement.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostalmanagement.app.DTO.CheckInOutDTO;
import com.hostalmanagement.app.DTO.ReservationDTO;
import com.hostalmanagement.app.dao.CheckInOutDAO;
import com.hostalmanagement.app.model.CheckInOut;

import java.util.List;
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
            checkInOut.getOutDate()
        );
    }


}
