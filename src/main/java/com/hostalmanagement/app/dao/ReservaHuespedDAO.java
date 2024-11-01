package com.hostalmanagement.app.dao;

import java.util.List;

import com.hostalmanagement.app.model.ReservaHuesped;

public interface ReservaHuespedDAO {
    ReservaHuesped findByNIF(String NIF);
    ReservaHuesped findByReserva(Long idReserva);
    List<ReservaHuesped> findAll();

    void save(ReservaHuesped reservaHuesped);
    void update(ReservaHuesped reservaHuesped);
    void removeByNIF(String NIF);

}
