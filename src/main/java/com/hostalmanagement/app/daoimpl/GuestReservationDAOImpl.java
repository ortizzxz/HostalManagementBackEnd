package com.hostalmanagement.app.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hostalmanagement.app.dao.GuestReservationDAO;
import com.hostalmanagement.app.model.GuestReservation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class GuestReservationDAOImpl implements GuestReservationDAO{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public GuestReservation findByNIF(String NIF) {
        return entityManager.createQuery("FROM reservaHuesped r WHERE r.NIFHuesped like :NIF", GuestReservation.class)
                .setParameter("NIF", NIF)
                .getSingleResult();
    }

    @Override
    public GuestReservation findByReservation(Long reservationId) {
        return entityManager.createQuery("FROM reservaHuesped r WHERE r.NIFHuesped like :idReserva", GuestReservation.class)
                .setParameter("idReserva", reservationId)
                .getSingleResult();
    }

    @Override
    public List<GuestReservation> findAll() {
        return entityManager.createQuery("FROM reservaHuesped r", GuestReservation.class)
                .getResultList();
    }

    @Override
    public void save(GuestReservation guestReservation) {
        entityManager.persist(guestReservation);
    }

    @Override
    public void update(GuestReservation guestReservation) {
        entityManager.merge(guestReservation);
    }

    @Override
    public void removeByNIF(String NIF) {
        GuestReservation guestReservation = findByNIF(NIF);
        if(guestReservation != null){
            entityManager.remove(guestReservation);
        }
    }
    
}
