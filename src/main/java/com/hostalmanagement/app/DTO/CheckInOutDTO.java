package com.hostalmanagement.app.DTO;

import java.time.LocalDateTime;

public class CheckInOutDTO {
    private Long id;
    private ReservationDTO reservationDTO;
    private LocalDateTime inDate;
    private LocalDateTime outTime;
    
    public CheckInOutDTO() {
    }

    public CheckInOutDTO(Long id, ReservationDTO reservationDTO, LocalDateTime inDate, LocalDateTime outTime) {
        this.id = id;
        this.reservationDTO = reservationDTO;
        this.inDate = inDate;
        this.outTime = outTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationDTO getReservationDTO() {
        return reservationDTO;
    }

    public void setReservationDTO(ReservationDTO reservationDTO) {
        this.reservationDTO = reservationDTO;
    }

    public LocalDateTime getInDate() {
        return inDate;
    }

    public void setInDate(LocalDateTime inDate) {
        this.inDate = inDate;
    }

    public LocalDateTime getOutTime() {
        return outTime;
    }

    public void setOutTime(LocalDateTime outTime) {
        this.outTime = outTime;
    }

    
}
