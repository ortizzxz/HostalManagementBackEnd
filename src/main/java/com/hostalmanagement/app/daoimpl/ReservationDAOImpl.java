package com.hostalmanagement.app.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.hostalmanagement.app.DTO.GuestDTO;
import com.hostalmanagement.app.DTO.ReservationDTO;
import com.hostalmanagement.app.dao.ReservationDAO;
import com.hostalmanagement.app.model.Guest;
import com.hostalmanagement.app.model.Reservation;
import com.hostalmanagement.app.model.Tenant;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class ReservationDAOImpl implements ReservationDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Reservation findById(Long id) {
        return entityManager.find(Reservation.class, id);
    }

    @Override
    public List<Reservation> findAll(Tenant tenant) {
        return entityManager.createQuery(
            "SELECT r FROM Reservation r WHERE r.room.tenant = :tenant", Reservation.class)
            .setParameter("tenant", tenant)
            .getResultList();
    }
    
    @Override
    public List<Reservation> findByConfirmed() {
        return entityManager.createQuery("from Reservation r where r.estado like :estado", Reservation.class)
                .setParameter("estado", Reservation.ReservationState.CONFIRMADA)
                .getResultList();
    }

    @Override
    public List<Reservation> findByCanceled() {
        return entityManager.createQuery("FROM Reservation r where r.estado like :estado", Reservation.class)
                .setParameter("estado", Reservation.ReservationState.CANCELADA)
                .getResultList();
    }

    @Override
    public List<Reservation> findByCompleted() {
        return entityManager.createQuery("FROM Reservation r where r.estado like :estado", Reservation.class)
                .setParameter("estado", Reservation.ReservationState.COMPLETADA)
                .getResultList();
    }

    @Override
    public Reservation findByRoom(Long roomId) {
        return entityManager.createQuery("FROM Reservation R WHERE idHabitacion like :idHabitacion", Reservation.class)
                .setParameter("idHabitacion", roomId)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void save(Reservation reservation) {
        entityManager.persist(reservation);
    }

    @Override
    @Transactional
    public void update(Reservation reservation) {
        entityManager.merge(reservation);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Reservation reservation = findById(id);
        if (reservation != null) {
            entityManager.remove(reservation);
        }
    }

    @Override
    public ReservationDTO toReservationDTO(Reservation reservation) {
        List<GuestDTO> guestDTOs = reservation.getGuests()
                .stream()
                .map(gr -> {
                    Guest g = gr.getGuest();
                    return new GuestDTO(
                            g.getNIF(),
                            g.getName(),
                            g.getLastname(),
                            g.getEmail(),
                            g.getPhone(),g.getTenant().getId());
                })
                .toList();

        ReservationDTO reservationDTO = new ReservationDTO(
                reservation.getId(),
                reservation.getRoom().getId(),
                reservation.getInDate(),
                reservation.getOutDate(),
                reservation.getState().toString(),
                guestDTOs,
                reservation.getRoom().getTenant().getId());
        return reservationDTO;
    }

}
