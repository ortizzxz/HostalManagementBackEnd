package com.hostalmanagement.app.dao;

import java.util.List;
import com.hostalmanagement.app.model.Reservation;

public interface ReservationDAO {
    Reserva findById(Long id);
    List<Reserva> findAll();

    List<Reserva> findByConfirmed();
    List<Reserva> findByCanceled();
    List<Reserva> findByCompleted();

    Reserva findByRoom(Long roomId);

    void save (Reservation reservation);
    void update (Reservation reservation);
    void delete (Long id);
}
