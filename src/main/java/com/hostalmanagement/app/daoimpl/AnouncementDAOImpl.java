package com.hostalmanagement.app.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hostalmanagement.app.dao.AnouncementDAO;
import com.hostalmanagement.app.model.Anouncement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class AnouncementDAOImpl implements AnouncementDAO {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Anouncement findById(Long id) {
        return entityManager.find(Anouncement.class, id);
    }

    @Override
    public List<Anouncement> getAllAnouncements() {
        return entityManager.createQuery("FROM Anouncement a", Anouncement.class).getResultList();
    }

    @Override
    public List<Anouncement> findByIdGreaterThan(long id) {
        return entityManager.createQuery("FROM Anouncement a WHERE a.id > :id", Anouncement.class)
                        .setParameter("id", id)
                        .getResultList();
    }

    @Override
    @Transactional
    public void save(Anouncement anouncement) {
        entityManager.persist(anouncement);
    }

    @Override
    @Transactional
    public void update(Anouncement anouncement) {
        entityManager.merge(anouncement);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Anouncement anouncement = findById(id);
        if(anouncement != null){
            entityManager.remove(anouncement);
        }
    }

}
