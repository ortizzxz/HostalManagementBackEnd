package com.hostalmanagement.app.DTO;

import java.time.LocalDate;
import java.util.List;

public class ReservationDTO {
    private Long roomId;
    private LocalDate inDate;
    private LocalDate outDate;
    private String state;
    private List<GuestDTO> guests;

    public ReservationDTO() {
    }

    public ReservationDTO(Long roomId, LocalDate inDate, LocalDate outDate, String state, List<GuestDTO> guests) {
        this.roomId = roomId;
        this.inDate = inDate;
        this.outDate = outDate;
        this.state = state;
        this.guests = guests;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public LocalDate getInDate() {
        return inDate;
    }

    public void setInDate(LocalDate inDate) {
        this.inDate = inDate;
    }

    public LocalDate getOutDate() {
        return outDate;
    }

    public void setOutDate(LocalDate outDate) {
        this.outDate = outDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<GuestDTO> getGuests() {
        return guests;
    }

    public void setGuests(List<GuestDTO> guests) {
        this.guests = guests;
    }

}
