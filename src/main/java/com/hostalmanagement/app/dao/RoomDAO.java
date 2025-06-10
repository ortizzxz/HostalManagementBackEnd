package com.hostalmanagement.app.dao;


import com.hostalmanagement.app.model.Room;
import java.util.List;


public interface RoomDAO{
    Room findById(Long id);
    Room findByRoomNumber(Long id);
    List<Room> findAll(Long tenantId);
    List<Room> findBetweenPrices(Double minPrice, Double maxPrice);
    List<Room> findAvailableRooms();

    void save(Room room);
    void update(Room room);
    void delete(Long id);
}