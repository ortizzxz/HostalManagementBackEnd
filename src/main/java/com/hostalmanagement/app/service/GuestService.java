package com.hostalmanagement.app.service;

import java.util.List;
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
        try{
            Guest guest = toEntity(guestDTO);
            guestDAO.save(guest);
            return toDTO(guest);
        } catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Couldn't create Guest: " + e.getMessage());
        }
    }

    public GuestDTO findGuestById(String NIF) {
        Guest guest = guestDAO.findByNIF(NIF);
        return (guest != null) ? toDTO(guest) : null;
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
            guest.getTenant().getId()
        );
    }

    private Guest toEntity(GuestDTO guestDTO) {
        Tenant tenant = tenantService.findById(guestDTO.getTenantId());

        return new Guest(
            guestDTO.getNif(),
            guestDTO.getName(),
            guestDTO.getLastname(),
            guestDTO.getEmail(),
            guestDTO.getPhone(),
            tenant        
            );
    }


    public GuestDTO updateGuest(String NIF, GuestDTO guestDTO) {
        Guest existingGuest = guestDAO.findByNIF(NIF);

        if (existingGuest != null) {    
            existingGuest.setName(guestDTO.getName());
            existingGuest.setLastname(guestDTO.getLastname());
            existingGuest.setEmail(guestDTO.getEmail());
            existingGuest.setPhone(guestDTO.getPhone());

            guestDAO.update(existingGuest);
            return toDTO(existingGuest);
        }
        return null;
    }

    public boolean deleteGuest(String NIF) {
        Guest existingGuest = guestDAO.findByNIF(NIF);
        if (existingGuest != null) {
            guestDAO.delete(NIF);
            return true;
        }
        return false;
    }

}
