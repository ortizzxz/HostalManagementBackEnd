package com.hostalmanagement.app.dao;


import com.hostalmanagement.app.model.Habitacion;
import java.util.List;


public interface HabitacionDAO{
    Habitacion findById(Long id);
    Habitacion findByRoomNumber(Long id);
    List<Habitacion> findAll();
    List<Habitacion> findBetweenPrices(Double precioMin, Double precioMax);
    List<Habitacion> findAvailableRooms();

    void save(Habitacion habitacion);
    void update(Habitacion habitacion);
    void delete(Long id);
}