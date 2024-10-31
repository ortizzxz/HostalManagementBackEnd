package com.hostalmanagement.app.daoimpl;

import java.util.List;

import com.hostalmanagement.app.dao.InventarioDAO;
import com.hostalmanagement.app.model.Inventario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class InventarioDAOImpl implements InventarioDAO{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Inventario findById(Long id) {
        return entityManager.find(Inventario.class, id);
    }

    @Override
    public List<Inventario> findAll() {
        return entityManager.createQuery("FROM Inventario i", Inventario.class).getResultList();
    }

    @Override
    public List<Inventario> findByKeyword(String item) {
        return entityManager.createQuery("FROM Inventario i WHERE i.item LIKE :item", Inventario.class)
                .setParameter("item", item)
                .getResultList();
    }

    @Override
    public List<Inventario> findByLowLevel() {
        return entityManager.createQuery("FROM Inventario i where i.nivelCritico > i.cantidad", Inventario.class).getResultList();
    }

    @Override
    @Transactional
    public void save(Inventario inventario) {
        entityManager.persist(inventario);
    }

    @Override
    @Transactional
    public void update(Inventario inventario) {
        entityManager.merge(inventario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Inventario inventario = findById(id);
        if(inventario != null) {
            entityManager.remove(inventario);
        }
    }
    
}
