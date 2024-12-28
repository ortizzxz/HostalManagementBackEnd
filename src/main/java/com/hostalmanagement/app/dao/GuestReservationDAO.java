package com.hostalmanagement.app.dao;

import java.util.List;

import com.hostalmanagement.app.model.GuestReservation;

public interface GuestReservationDAO {
    GuestReservation findByNIF(String NIF);
    GuestReservation findByReservation(Long reservationId);
    List<GuestReservation> findAll();

    void save(GuestReservation guestReservations);
    void update(GuestReservation guestReservation);
    void removeByNIF(String NIF);

}
