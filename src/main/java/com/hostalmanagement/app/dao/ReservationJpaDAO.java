package com.hostalmanagement.app.dao;

import com.hostalmanagement.app.model.Reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaDAO extends JpaRepository<Reservation, Long> {
    // You can still add custom queries if needed:
    List<Reservation> findByState(Reservation.ReservationState state);
}
