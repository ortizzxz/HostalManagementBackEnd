package com.hostalmanagement.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class GuestReservation {
    @Id
    @ManyToOne
    @JoinColumn(name  = "NIFHuesped", referencedColumnName = "NIF")
    private Guest guest;

    @Id
    @ManyToOne
    @JoinColumn(name = "idReserva", referencedColumnName = "id")
    private Reservation reservation; 

    public GuestReservation() { }

    public GuestReservation(Guest guest, Reservation reservation) {
        this.guest = guest;
        this.reservation = reservation;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }


}   
