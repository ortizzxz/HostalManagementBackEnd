package com.hostalmanagement.app.dao;

import java.util.List;

import com.hostalmanagement.app.model.GuestReservation;

public interface GuestReservationDAO {
    ReservaHuesped findByNIF(String NIF);
    ReservaHuesped findByReservation(Long rservationId);
    List<ReservaHuesped> findAll();

    void save(GuestReservation guestReservation );
    void update(GuestReservation guestReservation);
    void removeByNIF(String NIF);

}
