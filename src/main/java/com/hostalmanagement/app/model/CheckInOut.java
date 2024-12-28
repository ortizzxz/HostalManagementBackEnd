package com.hostalmanagement.app.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class CheckInOut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "idReserva") 
    private Reservation reservation;

    private LocalDateTime inDate;
    private LocalDateTime outDate;

    public CheckInOut() {}

    public CheckInOut(LocalDateTime inDate, LocalDateTime outDate, Reservation reservation) {
        this.inDate = inDate;
        this.outDate = outDate;
        this.reservation = reservation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
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

    @Override
    public String toString() {
        return "CheckInOut{ID=" + getId() + ", Reservation=" + getReservation() + ", In Date=" + getInDate()
                + ", Out Date=" + getOutDate() + "}";
    }

}
