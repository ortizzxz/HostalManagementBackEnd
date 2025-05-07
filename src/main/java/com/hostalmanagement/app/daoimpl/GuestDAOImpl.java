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
    public Optional<Guest> findByNIF(String NIF) {
        try {
            Guest guest = entityManager.createQuery("FROM Guest g WHERE g.NIF = :NIF", Guest.class)
                                       .setParameter("NIF", NIF)
                                       .getSingleResult();
            return Optional.ofNullable(guest);
        } catch (Error e) {
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
