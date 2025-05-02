package com.hostalmanagement.app.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class GuestReservationId implements Serializable {
    private String guest; // Assuming NIF is String
    private Long reservation;

    // Default constructor
    public GuestReservationId() {}

    // hashCode and equals REQUIRED
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GuestReservationId)) return false;
        GuestReservationId that = (GuestReservationId) o;
        return Objects.equals(guest, that.guest) &&
               Objects.equals(reservation, that.reservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guest, reservation);
    }

}
