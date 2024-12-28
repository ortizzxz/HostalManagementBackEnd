package com.hostalmanagement.app.dao;


import com.hostalmanagement.app.model.Room;
import java.util.List;


public interface RoomDAO{
    Habitacion findById(Long id);
    Habitacion findByRoomNumber(Long id);
    List<Habitacion> findAll();
    List<Habitacion> findBetweenPrices(Double minPrice, Double maxPrice);
    List<Habitacion> findAvailableRooms();

    void save(Room room);
    void update(Room room);
    void delete(Long id);
}