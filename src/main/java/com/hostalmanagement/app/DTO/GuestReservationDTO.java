package com.hostalmanagement.app.DTO;

import java.util.List;


public class GuestReservationDTO {
    private List<GuestDTO> guestDTO;
    private ReservationDTO reservationDTO; 

    public GuestReservationDTO() { }

    public GuestReservationDTO(List<GuestDTO> guestDTO, ReservationDTO reservationDTO) {
        this.guestDTO = guestDTO;
        this.reservationDTO = reservationDTO;
    }

    public List<GuestDTO> getGuestDTO() {
        return guestDTO;
    }

    public void setGuestDTO(List<GuestDTO> guestDTO) {
        this.guestDTO = guestDTO;
    }

    public ReservationDTO getReservationDTO() {
        return reservationDTO;
    }

    public void setReservationDTO(ReservationDTO reservationDTO) {
        this.reservationDTO = reservationDTO;
    }
    
}   
