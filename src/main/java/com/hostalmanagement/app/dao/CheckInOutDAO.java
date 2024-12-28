package com.hostalmanagement.app.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.hostalmanagement.app.model.CheckInOut;

public interface CheckInOutDAO {
    CheckInOut findById(Long id);
    List<CheckInOut> findAll();
    CheckInOut findByReservation(Long idReserva);
    List<CheckInOut> getByDateIn(LocalDateTime dateIn);
    List<CheckInOut> getByDateOut(LocalDateTime dateOut);

    void save(CheckInOut checkInOut);
    void update(CheckInOut checkInOut);
    void delete(Long id);
}
