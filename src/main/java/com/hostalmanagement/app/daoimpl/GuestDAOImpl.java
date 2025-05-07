package com.hostalmanagement.app.daoimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.hostalmanagement.app.DTO.GuestDTO;
import com.hostalmanagement.app.dao.GuestDAO;
import com.hostalmanagement.app.model.Guest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class GuestDAOImpl implements GuestDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Guest> findByNIF(String nif) {
        try {
            List<Guest> guests = entityManager.createQuery("FROM Guest g WHERE g.nif = :nif", Guest.class)
                                              .setParameter("nif", nif)
                                              .getResultList();
    
            // Return the first guest if the list is not empty, otherwise return Optional.empty()
            return guests.isEmpty() ? Optional.empty() : Optional.of(guests.get(0));
        } catch (Exception e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
            return Optional.empty();
        }
    }
    
    @Override
    public List<Guest> findAll() {
        return entityManager.createQuery("FROM Guest", Guest.class).getResultList();
    }

    @Override
    @Transactional
    public void save(Guest guest) {
        entityManager.persist(guest);
    }

    @Override
    @Transactional
    public void update(Guest guest) {
        entityManager.merge(guest);
    }

    @Override
    @Transactional
    public void delete(String NIF) {
        Optional<Guest> guestOpt = findByNIF(NIF);
        guestOpt.ifPresent(entityManager::remove);
    }

    @Override
    public GuestDTO toGuestDTO(Guest guest) {
        return new GuestDTO(
            guest.getNIF(),
            guest.getName(),
            guest.getLastname(),
            guest.getEmail(),
            guest.getPhone(),
            guest.getTenant().getId()
        );
    }
}
