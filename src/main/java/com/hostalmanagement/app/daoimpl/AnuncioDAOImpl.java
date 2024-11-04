package com.hostalmanagement.app.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hostalmanagement.app.dao.AnuncioDAO;
import com.hostalmanagement.app.model.Anuncio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class AnuncioDAOImpl implements AnuncioDAO {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Anuncio findById(Long id) {
        return entityManager.find(Anuncio.class, id);
    }

    @Override
    public List<Anuncio> getAllAnuncios() {
        return entityManager.createQuery("FROM Anuncio a", Anuncio.class).getResultList();
    }

    @Override
    @Transactional
    public void save(Anuncio anuncio) {
        entityManager.persist(anuncio);
    }

    @Override
    @Transactional
    public void update(Anuncio anuncio) {
        entityManager.merge(anuncio);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Anuncio anuncio = findById(id);
        if(anuncio != null){
            entityManager.remove(anuncio);
        }
    }
    
}
