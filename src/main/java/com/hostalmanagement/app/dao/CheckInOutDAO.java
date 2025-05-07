package com.hostalmanagement.app.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.hostalmanagement.app.model.CheckInOut;

public interface CheckInOutDAO {
    Optional<CheckInOut> findById(Long id);
    List<CheckInOut> findAll(Long tenantId);
    Optional<CheckInOut> findByReservation(Long reservationId);
    List<CheckInOut> getByInDate(LocalDateTime inDate);
    List<CheckInOut> getByOutDate(LocalDateTime outDate);

    void save(CheckInOut checkInOut);
    void update(CheckInOut checkInOut);
    void delete(Long id);
}
