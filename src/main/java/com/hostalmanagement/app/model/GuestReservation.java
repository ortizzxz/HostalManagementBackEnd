package com.hostalmanagement.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "guest_reservation")
@IdClass(GuestReservationId.class) // Composite key class
public class GuestReservation {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_reserva", referencedColumnName = "id")
    private Reservation reservation;

    @Id
    @ManyToOne
    @JoinColumn(name = "nifhuesped", referencedColumnName = "nif")
    private Guest guest;

    public GuestReservation() {}

    public GuestReservation(Guest guest, Reservation reservation) {
        this.reservation = reservation;
        this.guest = guest;
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