package com.hostalmanagement.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostalmanagement.app.DTO.AnouncementDTO;
import com.hostalmanagement.app.DTO.TenantDTO;
import com.hostalmanagement.app.dao.AnouncementDAO;
import com.hostalmanagement.app.dao.TenantDAO; // Assuming you have this
import com.hostalmanagement.app.model.Anouncement;
import com.hostalmanagement.app.model.Tenant;

@Service
public class AnouncementService {
    
    @Autowired
    private AnouncementDAO anouncementDAO;
    
    @Autowired
    private TenantService tenantService;
    
    @Autowired
    private TenantDAO tenantDAO; // Inject TenantDAO to get Tenant by ID

    // Devuelve todos los anuncios
    public List<AnouncementDTO> findAllAnouncements(Tenant tenant){
        List<Anouncement> anouncements = anouncementDAO.getAllAnouncements(tenant);
        return anouncements.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Buscar un anuncio por ID
    public AnouncementDTO findAnouncementById(Long id){
        Anouncement anouncement = anouncementDAO.findById(id);
        return (anouncement != null) ? toDTO(anouncement) : null;
    }

    // Crear un anuncio
    public AnouncementDTO createAnouncement(AnouncementDTO anouncementDTO) {
        try {
            Tenant tenant = tenantDAO.findById(anouncementDTO.getTenant().getId()); // Fetch tenant by ID
            if (tenant == null) {
                throw new IllegalArgumentException("Tenant not found");
            }
            Anouncement anouncement = toEntity(anouncementDTO, tenant);
            anouncementDAO.save(anouncement);
            return toDTO(anouncement);
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Error publicando el anuncio: " + e.getMessage());
        }
    }

    // Buscar anuncios con ID mayor a un valor específico
    public List<AnouncementDTO> findByIdGreaterThan(Long id) {
        List<Anouncement> anouncements = anouncementDAO.findByIdGreaterThan(id);
        return anouncements.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Actualizar un anuncio
    public AnouncementDTO updateAnouncement(Long id, AnouncementDTO anouncementDTO){
        Anouncement existingAnouncement = anouncementDAO.findById(id);
        if (existingAnouncement != null) {
            Tenant tenant = tenantDAO.findById(anouncementDTO.getTenant().getId());
            if (tenant == null) {
                throw new IllegalArgumentException("Tenant not found");
            }
            existingAnouncement.setTitle(anouncementDTO.getTitle());
            existingAnouncement.setContent(anouncementDTO.getContent());
            existingAnouncement.setPostDate(anouncementDTO.getPostDate());
            existingAnouncement.setExpirationDate(anouncementDTO.getExpirationDate());
            existingAnouncement.setTenant(tenant);

            anouncementDAO.update(existingAnouncement);
            return toDTO(existingAnouncement);
        }
        return null;
    }

    // Eliminar un anuncio
    public boolean deleteAnouncement(Long id){
        Anouncement existingAnouncement = anouncementDAO.findById(id);
        if (existingAnouncement != null) {
            anouncementDAO.delete(id);
            return true;
        }
        return false;
    }

    // Convertir entidad Anouncement a DTO
    private AnouncementDTO toDTO(Anouncement anouncement) {
        TenantDTO tenantDTO = tenantService.toDTO(anouncement.getTenant());
        return new AnouncementDTO(
            anouncement.getId(),
            anouncement.getTitle(),
            anouncement.getContent(),
            anouncement.getPostDate(),
            anouncement.getExpirationDate(),
            tenantDTO
        );
    }

    // Convertir DTO a entidad Anouncement
    private Anouncement toEntity(AnouncementDTO anouncementDTO, Tenant tenant) {
        return new Anouncement(
            anouncementDTO.getTitle(),
            anouncementDTO.getContent(),
            anouncementDTO.getPostDate(),
            anouncementDTO.getExpirationDate(),
            tenant
        );
    }
}
