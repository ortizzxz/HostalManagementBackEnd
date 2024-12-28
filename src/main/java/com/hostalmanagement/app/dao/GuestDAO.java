package com.hostalmanagement.app.dao;

import java.util.List;

import com.hostalmanagement.app.model.Guest;

public interface GuestDAO {
    Huesped findByNIF(String NIF);
    List<Huesped> findAll();

    void save(Guest guest);
    void update(Guest guest);
    void delete(String NIF);
}
