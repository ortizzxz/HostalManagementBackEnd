package com.hostalmanagement.app.dao;

import java.util.List;

import com.hostalmanagement.app.model.Bill;

public interface BillDAO {
    Factura findById(Long id);
    List<Factura> findByRoomId(Long idReservation);
    List<Factura> findAll();
    List<Factura> findAllPending();
    List<Factura> findAllCanceled();
    List<Factura> findAllPaid();

    void save(Bill bill);
    void delete(Long id);
}
