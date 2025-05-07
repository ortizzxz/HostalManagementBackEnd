package com.hostalmanagement.app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostalmanagement.app.DTO.GuestDTO;
import com.hostalmanagement.app.DTO.TenantDTO;
import com.hostalmanagement.app.dao.GuestDAO;
import com.hostalmanagement.app.model.Guest;
import com.hostalmanagement.app.model.Tenant;

@Service
public class GuestService {

    @Autowired
    private GuestDAO guestDAO;

    @Autowired
    private TenantService tenantService;

    public GuestDTO createGuest(GuestDTO guestDTO) {
        try {
            Guest guest = toEntity(guestDTO);
            guestDAO.save(guest);
            return toDTO(guest);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Couldn't create Guest: " + e.getMessage());
        }
    }

    // Find a guest by NIF and return a DTO or throw an exception
    public GuestDTO findGuestById(String NIF) {
        Optional<Guest> guestOpt = guestDAO.findByNIF(NIF);
        return guestOpt.map(this::toDTO).orElse(null); // Returns the DTO or null if not found
    }

    public List<GuestDTO> findAllGuests() {
        List<Guest> guests = guestDAO.findAll();
        return guests.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private GuestDTO toDTO(Guest guest) {
        return new GuestDTO(
                guest.getNIF(),
                guest.getName(),
                guest.getLastname(),
                guest.getEmail(),
                guest.getPhone(),
                guest.getTenant().getId());
    }

    private Guest toEntity(GuestDTO guestDTO) {
        Tenant tenant = tenantService.findById(guestDTO.getTenantId());

        return new Guest(
                guestDTO.getNif(),
                guestDTO.getName(),
                guestDTO.getLastname(),
                guestDTO.getEmail(),
                guestDTO.getPhone(),
                tenant);
    }

    // Update a guest and return the updated DTO
    public GuestDTO updateGuest(String NIF, GuestDTO guestDTO) {
        Optional<Guest> existingGuestOpt = guestDAO.findByNIF(NIF);

        if (existingGuestOpt.isPresent()) {
            Guest existingGuest = existingGuestOpt.get();
            // Update guest details
            existingGuest.setName(guestDTO.getName());
            existingGuest.setLastname(guestDTO.getLastname());
            existingGuest.setEmail(guestDTO.getEmail());
            existingGuest.setPhone(guestDTO.getPhone());

            guestDAO.update(existingGuest);
            return toDTO(existingGuest); // Return updated GuestDTO
        }
        return null; // Or you can throw a custom exception, e.g., GuestNotFoundException
    }

    // Delete a guest by NIF and return success status
    public boolean deleteGuest(String NIF) {
        Optional<Guest> existingGuestOpt =  guestDAO.findByNIF(NIF);

        if (existingGuestOpt.isPresent()) {
            guestDAO.delete(NIF); // Delete the guest
            return true; // Return true on successful deletion
        }
        return false; // Return false if guest was not found
    }

}
