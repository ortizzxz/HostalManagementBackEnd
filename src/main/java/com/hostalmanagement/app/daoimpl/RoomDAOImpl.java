package com.hostalmanagement.app.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hostalmanagement.app.dao.RoomDAO;
import com.hostalmanagement.app.model.Room;
import com.hostalmanagement.app.model.Room.RoomState;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class RoomDAOImpl implements RoomDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Room findById(Long id) {
        return entityManager.find(Room.class, id);
    }

    @Override
    public Room findByRoomNumber(Long id) {
        return entityManager.createQuery("from Habitacion h where h.numero = :id", Room.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Room> findAll() {
        return entityManager.createQuery("from Habitacion", Room.class).getResultList(); 
    }

    @Override
    @Transactional
    public void save(Room room) {
        entityManager.persist(room);
    }

    @Override
    @Transactional
    public void update(Room room) {
        entityManager.merge(room);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Room room = findById(id);
        if (room != null) {
            entityManager.remove(room);
        }
    }

    @Override
    public List<Room> findBetweenPrices(Double minPrice, Double maxPrice) {
        return entityManager
                .createQuery("FROM Habitacion h WHERE h.tarifaBase BETWEEN :precioMin AND :precioMax", Room.class)
                .setParameter("precioMin", minPrice).setParameter("precioMax", maxPrice)
                .getResultList();
    }

    @Override
    public List<Room> findAvailableRooms() {
        return entityManager.createQuery("FROM Habitacion h WHERE h.estado = :estadoDisponible", Room.class)
                .setParameter("estadoDisponible", RoomState.DISPONIBLE)
                .getResultList();
    }

}
