package com.hostalmanagement.app.model;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_habitacion", nullable = false)
    private Room room;

    @Column(nullable = false)
    private LocalDateTime inDate;

    @Column(nullable = false)
    private LocalDateTime outDate;

    @Enumerated(EnumType.STRING)
    private ReservationState state; 

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GuestReservation> guests;

    public enum ReservationState{
        CONFIRMADA, CANCELADA, COMPLETADA
    }
    
    public Reservation() {
    }

    public Reservation(Room room, LocalDateTime inDate, LocalDateTime outDate, ReservationState state, List<GuestReservation> guestReservations) {
        this.room = room;
        this.inDate = inDate;
        this.outDate = outDate;
        this.state = state;
        this.guests = guestReservations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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

    public List<GuestReservation> getGuests() {
        return guests;
    }

    public void setGuests(List<GuestReservation> guests) {
        this.guests = guests;
    }

    public ReservationState getState() {
        return state;
    }

    public void setStatus(ReservationState state) {
        this.state = state;
    }
}
