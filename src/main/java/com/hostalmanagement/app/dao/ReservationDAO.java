package com.hostalmanagement.app.dao;

import java.util.List;
import com.hostalmanagement.app.model.Reservation;

public interface ReservationDAO {
    Reservation findById(Long id);
    List<Reservation> findAll();

    List<Reservation> findByConfirmed();
    List<Reservation> findByCanceled();
    List<Reservation> findByCompleted();

    Reservation findByRoom(Long roomId);

    void save (Reservation reservation);
    void update (Reservation reservation);
    void delete (Long id);
}
