package com.hostalmanagement.app.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hostalmanagement.app.dao.TenantDAO;
import com.hostalmanagement.app.model.Tenant;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class TenantDAOImpl implements TenantDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tenant findById(Long id) {
        return entityManager.find(Tenant.class, id);
    }

    @Override
    public List<Tenant> findAll() {
        return entityManager.createQuery("FROM Tenant", Tenant.class).getResultList();
    }

    @Override
    public void save(Tenant tenant) {
        entityManager.persist(tenant);
    }

    @Override
    public void update(Tenant tenant) {
        entityManager.merge(tenant);
    }

    @Override
    public void remove(Long id) {
        Tenant tenant = findById(id);
        if (tenant != null) {
            entityManager.remove(tenant);
        }
    }
}
