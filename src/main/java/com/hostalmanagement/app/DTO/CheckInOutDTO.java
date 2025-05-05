package com.hostalmanagement.app.DTO;

import java.time.LocalDateTime;

public class CheckInOutDTO {
    private Long id;
    private ReservationDTO reservationDTO;
    private LocalDateTime inDate;
    private LocalDateTime ouTime;
    
    public CheckInOutDTO() {
    }

    public CheckInOutDTO(Long id, ReservationDTO reservationDTO, LocalDateTime inDate, LocalDateTime ouTime) {
        this.id = id;
        this.reservationDTO = reservationDTO;
        this.inDate = inDate;
        this.ouTime = ouTime;
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

    public LocalDateTime getOuTime() {
        return ouTime;
    }

    public void setOuTime(LocalDateTime ouTime) {
        this.ouTime = ouTime;
    }

    
}
