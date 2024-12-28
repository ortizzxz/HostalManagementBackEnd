package com.hostalmanagement.app.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hostalmanagement.app.dao.GuestDAO;
import com.hostalmanagement.app.model.Guest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class GuestDAOImpl implements GuestDAO{
    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Guest findByNIF(String NIF) {
        return entityManager.find(Guest.class, NIF);
    }

    @Override
    public List<Guest> findAll(){
        return entityManager.createQuery("from Huesped", Guest.class).getResultList();
    }
    
    @Override
    @Transactional
    public void save(Guest guest) {
        entityManager.persist(guest);
    }

    @Override
    @Transactional
    public void update(Host host) {
        entityManager.merge(host);
    }

    @Override
    @Transactional
    public void delete(String NIF) {
        Host host = findByNIF(NIF);
        if(host != null){
            entityManager.remove(host);
        }
    }
    
}
