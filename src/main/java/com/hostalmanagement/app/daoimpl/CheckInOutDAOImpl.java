package com.hostalmanagement.app.daoimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Optional<CheckInOut> findById(final Long id) {
        return Optional.ofNullable(entityManager.find(CheckInOut.class, id));
    }
    
    @Override
    public List<CheckInOut> findAll(Long tenantId) {
        return entityManager.createQuery(
                "FROM CheckInOut c WHERE c.reservation.room.tenant.id = :tenantId",
                CheckInOut.class)
                .setParameter("tenantId", tenantId)
                .getResultList();
    }

    @Override
    public Optional<CheckInOut> findByReservation(final Long reservationId) {
        List<CheckInOut> results = entityManager.createQuery(
                "FROM CheckInOut c WHERE c.reservation.id = :idReserva",
                CheckInOut.class)
                .setParameter("idReserva", reservationId)
                .getResultList();
    
        return results.stream().findFirst(); // returns Optional.empty() if list is empty
    }
    
    @Override
    public List<CheckInOut> getByInDate(LocalDateTime fechaIn) {
        return entityManager.createQuery(
                "FROM CheckInOut c WHERE c.inDate = :fechaIn",
                CheckInOut.class)
                .setParameter("fechaIn", fechaIn)
                .getResultList();
    }

    @Override
    public List<CheckInOut> getByOutDate(LocalDateTime fechaOut) {
        return entityManager.createQuery(
                "FROM CheckInOut c WHERE c.outDate = :fechaOut",
                CheckInOut.class)
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
        entityManager.merge(checkInOut);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<CheckInOut> checkInOut = findById(id);
        if (checkInOut != null) {
            entityManager.remove(checkInOut);
        }
    }

}
