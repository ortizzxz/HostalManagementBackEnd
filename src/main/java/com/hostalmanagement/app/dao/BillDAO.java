package com.hostalmanagement.app.dao;

import java.util.List;

import com.hostalmanagement.app.model.Bill;

public interface BillDAO {
    Bill findById(Long id);
    List<Bill> findByRoomId(Long idReservation);
    List<Bill> findAll();
    List<Bill> findAllPending();
    List<Bill> findAllCanceled();
    List<Bill> findAllPaid();

    void save(Bill bill);
    void delete(Long id);
}
