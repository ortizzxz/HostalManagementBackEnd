package com.hostalmanagement.app.dao;

import java.util.List;

import com.hostalmanagement.app.DTO.ReservationDTO;
import com.hostalmanagement.app.model.Reservation;
import com.hostalmanagement.app.model.Tenant;

public interface ReservationDAO {
    Reservation findById(Long id);
    List<Reservation> findAll(Tenant tenant);

    List<Reservation> findByConfirmed();
    List<Reservation> findByCanceled();
    List<Reservation> findByCompleted();

    Reservation findByRoom(Long roomId);

    ReservationDTO toReservationDTO(Reservation reservation);
    void save (Reservation reservation);
    void update (Reservation reservation);
    void delete (Long id);
}
