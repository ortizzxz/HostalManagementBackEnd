package com.hostalmanagement.app.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationDTO {
    private Long id;
    private Long roomId;
    private LocalDateTime inDate;
    private LocalDateTime outDate;
    private String state;
    private List<GuestDTO> guests;
    private Long tenantId;

    public ReservationDTO() {
    }

    public ReservationDTO(Long id, Long roomId, LocalDateTime inDate, LocalDateTime outDate, String state,
            List<GuestDTO> guests, Long tenantId) {
        this.id = id;
        this.roomId = roomId;
        this.inDate = inDate;
        this.outDate = outDate;
        this.state = state;
        this.guests = guests;
        this.tenantId = tenantId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public LocalDateTime getInDate() {
        return inDate;
    }

    public void setInDate(LocalDateTime inDate) {
        this.inDate = inDate;
    }

    public LocalDateTime getOutDate() {
        return outDate;
    }

    public void setOutDate(LocalDateTime outDate) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
