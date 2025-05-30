package com.hostalmanagement.app.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hostalmanagement.app.dao.InventoryDAO;
import com.hostalmanagement.app.model.Inventory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class InventoryDAOImpl implements InventoryDAO{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Inventory findById(Long id) {
        return entityManager.find(Inventory.class, id);
    }

    @Override
    public List<Inventory> findAll(long tenantId) {
        return entityManager.createQuery("FROM Inventory i WHERE i.tenant.id = :tenantId", Inventory.class)
                        .setParameter("tenantId", tenantId)
                        .getResultList();
    }

    @Override
    public List<Inventory> findByKeyword(String item) {
        return entityManager.createQuery("FROM Inventory i WHERE i.item LIKE :item", Inventory.class)
                .setParameter("item", item)
                .getResultList();
    }

    @Override
    public List<Inventory> findByWarningLevel() {
        return entityManager.createQuery("FROM Inventory i where i.nivelCritico > i.cantidad", Inventory.class).getResultList();
    }

    @Override
    @Transactional
    public void save(Inventory inventory) {
        entityManager.persist(inventory);
    }

    @Override
    @Transactional
    public void update(Inventory inventory) {
        entityManager.merge(inventory);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Inventory inventory = findById(id);
        if(inventory != null) {
            entityManager.remove(inventory);
        }
    }
    
}
