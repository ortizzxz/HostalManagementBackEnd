package com.hostalmanagement.app.daoimpl;

import java.util.List;

import com.hostalmanagement.app.dao.ReservaDAO;
import com.hostalmanagement.app.model.Reserva;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class ReservaDAOImpl implements ReservaDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Reserva findById(Long id) {
        return entityManager.find(Reserva.class, id);
    }

    @Override
    public List<Reserva> findAll() {
        return entityManager.createQuery("from Reserva", Reserva.class).getResultList();
    }

    @Override
    public List<Reserva> findByConfirmadas() {
        return entityManager.createQuery("from Reserva r where r.estado like :estado", Reserva.class)
                .setParameter("estado", Reserva.EstadoReserva.CONFIRMADA)
                .getResultList();
    }

    @Override
    public List<Reserva> findByCanceladas() {
        return entityManager.createQuery("FROM Reserva r where r.estado like :estado", Reserva.class)
                .setParameter("estado", Reserva.EstadoReserva.CANCELADA)
                .getResultList();
    }

    @Override
    public List<Reserva> findByCompletadas() {
        return entityManager.createQuery("FROM Reserva r where r.estado like :estado", Reserva.class)
                .setParameter("estado", Reserva.EstadoReserva.COMPLETADA)
                .getResultList();
    }

    @Override
    public Reserva findByRoom(Long idHabitacion) {
        return entityManager.createQuery("FROM Reserva R WHERE idHabitacion like :idHabitacion", Reserva.class)
                .setParameter("idHabitacion", idHabitacion)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void save(Reserva reserva) {
        entityManager.persist(reserva);        
    }

    @Override
    @Transactional
    public void update(Reserva reserva) {
        entityManager.merge(reserva);        
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Reserva reserva = findById(id);
        if(reserva != null){
            entityManager.remove(reserva);
        }
    }

}
