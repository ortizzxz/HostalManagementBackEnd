package com.hostalmanagement.app.dao;

import java.util.List;

import com.hostalmanagement.app.model.Factura;

public interface FacturaDAO {
    Factura findById(Long id);
    List<Factura> findByRoomId(Long idReserva);
    List<Factura> findAll();
    List<Factura> findAllPendiente();
    List<Factura> findAllCancelada();
    List<Factura> findAllPagada();

    void save(Factura factura);
    void delete(Long id);
}
