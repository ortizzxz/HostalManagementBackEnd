package com.hostalmanagement.app.dao;

import java.util.List;
import java.util.Optional;

import com.hostalmanagement.app.DTO.GuestDTO;
import com.hostalmanagement.app.model.Guest;

public interface GuestDAO {
    Optional<Guest> findByNIF(String NIF);
    GuestDTO toGuestDTO(Guest guest);
    List<Guest> findAll();

    Guest save(Guest guest);
    void update(Guest guest);
    void delete(String NIF);
}
