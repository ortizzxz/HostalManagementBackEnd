package com.hostalmanagement.app.daoimpl;

import java.util.List;

import com.hostalmanagement.app.dao.HuespedDAO;
import com.hostalmanagement.app.model.Huesped;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class HuespedDAOImpl implements HuespedDAO{
    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Huesped findByNIF(String NIF) {
        return entityManager.find(Huesped.class, NIF);
    }

    @Override
    public List<Huesped> findAll(){
        return entityManager.createQuery("from Huesped", Huesped.class).getResultList();
    }
    
    @Override
    @Transactional
    public void save(Huesped huesped) {
        entityManager.persist(huesped);
    }

    @Override
    @Transactional
    public void update(Huesped huesped) {
        entityManager.merge(huesped);
    }

    @Override
    @Transactional
    public void delete(String NIF) {
        Huesped huesped = findByNIF(NIF);
        if(huesped != null){
            entityManager.remove(huesped);
        }
    }
    
}
