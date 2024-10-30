package com.hostalmanagement.app.dao;

import java.util.List;

import com.hostalmanagement.app.model.Huesped;

public interface HuespedDAO {
    Huesped findByNIF(String NIF);
    List<Huesped> findAll();

    void save(Huesped huesped);
    void update(Huesped huesped);
    void delete(String NIF);
}
