package com.hostalmanagement.app.daoimpl;

import com.hostalmanagement.app.dao.WageDAO;
import com.hostalmanagement.app.model.User;
import com.hostalmanagement.app.model.Wage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WageDAOImpl implements WageDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Wage findById(Long id) {
        return entityManager.find(Wage.class, id);
    }

    public Optional<Wage> findByUser(User user) {
        List<Wage> results = entityManager
                .createQuery("FROM Wage w WHERE w.user = :user", Wage.class)
                .setParameter("user", user)
                .getResultList();

        return results.stream().findFirst();
    }

    @Override
    public List<Wage> findAll() {
        return entityManager.createQuery("FROM Wage", Wage.class).getResultList();
    }

    @Override
    public List<Wage> findByUserId(Long userId) {
        return entityManager.createQuery("FROM Wage w WHERE w.user.id = :userId", Wage.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Wage> findByTenantId(Long tenantId) {
        return entityManager.createQuery(
                "SELECT w FROM Wage w WHERE w.user.tenant.id = :tenantId", Wage.class)
                .setParameter("tenantId", tenantId)
                .getResultList();
    }

    @Override
    @Transactional
    public void save(Wage wage) {
        entityManager.persist(wage);
    }

    @Override
    @Transactional
    public void update(Wage wage) {
        entityManager.merge(wage);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Wage wage = findById(id);
        if (wage != null) {
            entityManager.remove(wage);
        }
    }
}
