package com.hostalmanagement.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostalmanagement.app.DTO.AnouncementDTO;
import com.hostalmanagement.app.dao.AnouncementDAO;
import com.hostalmanagement.app.model.Anouncement;

@Service
public class AnouncementService {
    @Autowired
    private AnouncementDAO anouncementDAO;

    public List<AnouncementDTO> findAllAnouncements(){
        List<Anouncement> anouncements = anouncementDAO.getAllAnouncements();
        return anouncements.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AnouncementDTO findAnouncementById(Long id){
        Anouncement anouncement = anouncementDAO.findById(id);
        return (anouncement != null) ? toDTO(anouncement) : null;
    }

    public AnouncementDTO createAnouncement(AnouncementDTO anouncementDTO) {
        try{
            Anouncement anouncement = toEntity(anouncementDTO);
            anouncementDAO.save(anouncement);
            return toDTO(anouncement);
        } catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Error publicando el anuncio: " + e.getMessage()); 
        }
    }
    
    public List<AnouncementDTO> findByIdGreaterThan(long id) {
        List<Anouncement> anouncements = anouncementDAO.findByIdGreaterThan(id);
        return anouncements.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AnouncementDTO updateAnouncement(Long id, AnouncementDTO anouncementDTO){
        Anouncement existingAnouncement = anouncementDAO.findById(id);
        if(existingAnouncement != null){
            existingAnouncement.setTitle(anouncementDTO.getTitle());
            existingAnouncement.setContent(anouncementDTO.getContent());
            existingAnouncement.setPostDate(anouncementDTO.getPostDate());
            existingAnouncement.setExpirationDate(anouncementDTO.getExpirationDate());

            anouncementDAO.update(existingAnouncement);
            return toDTO(existingAnouncement);
        }

        return null;
    }

    public boolean deleteAnouncement(Long id){
        Anouncement existingAnouncement = anouncementDAO.findById(id);
        if(existingAnouncement != null){
            anouncementDAO.delete(id);
            return true;
        }
        return false;
    }

    private AnouncementDTO toDTO(Anouncement anouncement) {
        return new AnouncementDTO(anouncement.getId(), anouncement.getTitle(), anouncement.getContent(), anouncement.getPostDate(), anouncement.getExpirationDate());
    }

    private Anouncement toEntity(AnouncementDTO anouncementDTO) {
        return new Anouncement(
            anouncementDTO.getTitle(),
            anouncementDTO.getContent(),
            anouncementDTO.getPostDate(),
            anouncementDTO.getExpirationDate());
    }


}


    // // Devuelve todos los inventarios
    // public List<InventoryDTO> findAllInventories() {
    //     List<Inventory> inventories = inventoryDAO.findAll();
    //     return inventories.stream().map(this::toDTO).collect(Collectors.toList());
    // }
