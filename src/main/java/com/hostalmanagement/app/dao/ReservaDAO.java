package com.hostalmanagement.app.dao;

import java.util.List;
import com.hostalmanagement.app.model.Reserva;

public interface ReservaDAO {
    Reserva findById(Long id);
    List<Reserva> findAll();

    List<Reserva> findByConfirmadas();
    List<Reserva> findByCanceladas();
    List<Reserva> findByCompletadas();

    Reserva findByRoom(Long idHabitacion);

    void save (Reserva reserva);
    void update (Reserva reserva);
    void delete (Long id);
}
