package com.hostalmanagement.app.daoimpl;

import java.util.List;

import com.hostalmanagement.app.dao.FacturaDAO;
import com.hostalmanagement.app.model.Factura;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class FacturaDAOImpl implements FacturaDAO{
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public Factura findById(Long id) {
        return entityManager.find(Factura.class, id);
    }

    @Override
    public List<Factura> findByRoomId(Long idReserva) {
        return entityManager.createQuery("FROM Factura f WHERE f.idReserva like :idReserva", Factura.class)
                .setParameter("idReserva", idReserva)
                .getResultList();
    }
    
    @Override
    public List<Factura> findAll() {
        return entityManager.createQuery("FROM Factura f", Factura.class).getResultList();
    }
    
    @Override
    public List<Factura> findAllPendiente() {
        return entityManager.createQuery("FROM Factura f WHERE f.estado like :estado", Factura.class)
                    .setParameter("estado", Factura.EstadoFactura.PENDIENTE)
                    .getResultList();
    }

    @Override
    public List<Factura> findAllCancelada() {
        return entityManager.createQuery("FROM Factura f where f.estado like :estado", Factura.class)
                .setParameter("estado", Factura.EstadoFactura.CANCELADA)
                .getResultList();
    }

    @Override
    public List<Factura> findAllPagada() {
        return entityManager.createQuery("FROM Factura f where f.estado like :estado", Factura.class)
                .setParameter("estado", Factura.EstadoFactura.PAGADA).
                getResultList();
    }

    @Override
    @Transactional
    public void save(Factura factura) {
        entityManager.persist(factura);
    }

    @Override
    public void delete(Long id) {
        Factura factura = findById(id);
        if (factura != null){
            entityManager.remove(factura);
        }
    }
}
