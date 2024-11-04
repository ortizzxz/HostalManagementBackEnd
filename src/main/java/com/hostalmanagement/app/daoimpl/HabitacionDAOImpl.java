package com.hostalmanagement.app.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hostalmanagement.app.dao.HabitacionDAO;
import com.hostalmanagement.app.model.Habitacion;
import com.hostalmanagement.app.model.Habitacion.EstadoHabitacion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class HabitacionDAOImpl implements HabitacionDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Habitacion findById(Long id) {
        return entityManager.find(Habitacion.class, id);
    }

    @Override
    public Habitacion findByRoomNumber(Long id) {
        return entityManager.createQuery("from Habitacion h where h.numero = :id", Habitacion.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Habitacion> findAll() {
        return entityManager.createQuery("from Habitacion", Habitacion.class).getResultList(); 
    }

    @Override
    @Transactional
    public void save(Habitacion habitacion) {
        entityManager.persist(habitacion);
    }

    @Override
    @Transactional
    public void update(Habitacion habitacion) {
        entityManager.merge(habitacion);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Habitacion habitacion = findById(id);
        if (habitacion != null) {
            entityManager.remove(habitacion);
        }
    }

    @Override
    public List<Habitacion> findBetweenPrices(Double precioMin, Double precioMax) {
        return entityManager
                .createQuery("FROM Habitacion h WHERE h.tarifaBase BETWEEN :precioMin AND :precioMax", Habitacion.class)
                .setParameter("precioMin", precioMin).setParameter("precioMax", precioMax)
                .getResultList();
    }

    @Override
    public List<Habitacion> findAvailableRooms() {
        return entityManager.createQuery("FROM Habitacion h WHERE h.estado = :estadoDisponible", Habitacion.class)
                .setParameter("estadoDisponible", EstadoHabitacion.DISPONIBLE)
                .getResultList();
    }

}
