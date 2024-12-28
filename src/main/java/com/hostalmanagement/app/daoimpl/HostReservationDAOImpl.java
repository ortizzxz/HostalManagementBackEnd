package com.hostalmanagement.app.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hostalmanagement.app.dao.ReservaHuespedDAO;
import com.hostalmanagement.app.model.ReservaHuesped;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class HostReservationDAOImpl implements HostReservationDAO{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ReservaHuesped findByNIF(String NIF) {
        return entityManager.createQuery("FROM reservaHuesped r WHERE r.NIFHuesped like :NIF", ReservaHuesped.class)
                .setParameter("NIF", NIF)
                .getSingleResult();
    }

    @Override
    public ReservaHuesped findByReserva(Long idReserva) {
        return entityManager.createQuery("FROM reservaHuesped r WHERE r.NIFHuesped like :idReserva", ReservaHuesped.class)
                .setParameter("idReserva", idReserva)
                .getSingleResult();
    }

    @Override
    public List<ReservaHuesped> findAll() {
        return entityManager.createQuery("FROM reservaHuesped r", ReservaHuesped.class)
                .getResultList();
    }

    @Override
    public void save(ReservaHuesped reservaHuesped) {
        entityManager.persist(reservaHuesped);
    }

    @Override
    public void update(ReservaHuesped reservaHuesped) {
        entityManager.merge(reservaHuesped);
    }

    @Override
    public void removeByNIF(String NIF) {
        ReservaHuesped reservaHuesped = findByNIF(NIF);
        if(reservaHuesped != null){
            entityManager.remove(reservaHuesped);
        }
    }
    
}
