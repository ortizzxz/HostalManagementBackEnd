package com.hostalmanagement.app.daoimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hostalmanagement.app.dao.CheckInOutDAO;
import com.hostalmanagement.app.model.CheckInOut;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class CheckInOutDAOImpl implements CheckInOutDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public CheckInOut findById(final Long id) {
        return entityManager.find(CheckInOut.class, id);
    }
    
    @Override
    public List<CheckInOut> findAll() {
        return entityManager.createQuery("FROM CheckInOut c", CheckInOut.class).getResultList();
    }

    @Override
    public CheckInOut findByReservation(final Long reservationId) {
        return entityManager.createQuery("FROM CheckInOut c WHERE c.idReserva LIKE :idReserva", CheckInOut.class)
                .setParameter("idReserva", reservationId)
                .getSingleResult();
    }

    @Override
    public List<CheckInOut> getByDateIn(LocalDateTime fechaIn) {
        return entityManager.createQuery("FROM CheckInOut c WHERE c.fechaIn LIKE :fechaIn", CheckInOut.class)
                .setParameter("fechaIn", fechaIn)
                .getResultList();
    }

    @Override
    public List<CheckInOut> getByDateOut(LocalDateTime fechaOut) {
        return entityManager.createQuery("FROM CheckInOut c WHERE c.fechaOut LIKE :fechaOut", CheckInOut.class)
                .setParameter("fechaOut", fechaOut)
                .getResultList();
    }


    @Override
    @Transactional
    public void save(CheckInOut checkInOut) {
        entityManager.persist(checkInOut);
    }

    @Override
    @Transactional
    public void update(CheckInOut checkInOut) {
        entityManager.persist(checkInOut);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        CheckInOut checkInOut = findById(id);
        if(checkInOut != null){
            entityManager.remove(checkInOut);
        }
    }
    
}
