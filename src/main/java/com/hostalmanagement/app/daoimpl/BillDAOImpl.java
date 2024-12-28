package com.hostalmanagement.app.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hostalmanagement.app.dao.BillDAO;
import com.hostalmanagement.app.model.Bill;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class BillDAOImpl implements BillDAO{
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public Bill findById(Long id) {
        return entityManager.find(Bill.class, id);
    }

    @Override
    public List<Bill> findByRoomId(Long reservationId) {
        return entityManager.createQuery("FROM Factura f WHERE f.idReserva like :idReserva", Bill.class)
                .setParameter("idReserva", reservationId)
                .getResultList();
    }
    
    @Override
    public List<Bill> findAll() {
        return entityManager.createQuery("FROM Factura f", Bill.class).getResultList();
    }
    
    @Override
    public List<Bill> findAllPending() {
        return entityManager.createQuery("FROM Factura f WHERE f.estado like :estado", Bill.class)
                    .setParameter("estado", Bill.BillState.PENDIENTE)
                    .getResultList();
    }

    @Override
    public List<Bill> findAllCanceled() {
        return entityManager.createQuery("FROM Factura f where f.estado like :estado", Bill.class)
                .setParameter("estado", Bill.BillState.CANCELADA)
                .getResultList();
    }

    @Override
    public List<Bill> findAllPaid() {
        return entityManager.createQuery("FROM Factura f where f.estado like :estado", Bill.class)
                .setParameter("estado", Bill.BillState.PAGADA).
                getResultList();
    }

    @Override
    @Transactional
    public void save(Bill bill) {
        entityManager.persist(bill);
    }

    @Override
    public void delete(Long id) {
        Bill bill = findById(id);
        if (bill != null){
            entityManager.remove(bill);
        }
    }
}
