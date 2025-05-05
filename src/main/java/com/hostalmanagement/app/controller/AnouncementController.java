package com.hostalmanagement.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hostalmanagement.app.service.RoomService;
import com.hostalmanagement.app.service.TenantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import com.hostalmanagement.app.HostalManagementApplication;
import com.hostalmanagement.app.DTO.AnouncementDTO;
import com.hostalmanagement.app.config.SecurityConfig;
import com.hostalmanagement.app.dao.AnouncementDAO;
import com.hostalmanagement.app.model.Tenant;
import com.hostalmanagement.app.service.AnouncementService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/announcement")
public class AnouncementController {

    private final SecurityFilterChain securityFilterChain;

    private final RoomService roomService;

    private final RoomController roomController;
    
    private final SecurityConfig securityConfig;
    private final HostalManagementApplication hostalManagementApplication;

    @Autowired
    private AnouncementService anouncementService;
    
    @Autowired
    private TenantService tenantService;

    AnouncementController(SecurityConfig securityConfig, 
                            HostalManagementApplication hostalManagementApplication, RoomController roomController, RoomService roomService, SecurityFilterChain securityFilterChain){
        this.hostalManagementApplication = hostalManagementApplication;
        this.securityConfig = securityConfig;
        this.roomController = roomController;
        this.roomService = roomService;
        this.securityFilterChain = securityFilterChain;
    }

    //Buscar todas los anuncions
    @GetMapping
    public ResponseEntity<List<AnouncementDTO>> findAllAnouncements(Long tenantId){
        Tenant tenant = tenantService.findById(tenantId);

        
        List<AnouncementDTO> anouncements = anouncementService.findAllAnouncements(tenant);
        return ResponseEntity.ok(anouncements);
    }

    //Buscar un anuncio por ID 
    @GetMapping("/{id}")
    public ResponseEntity<AnouncementDTO> findAnouncementById(@PathVariable Long id){
        AnouncementDTO anouncement = anouncementService.findAnouncementById(id);
        return (anouncement != null) ? ResponseEntity.ok(anouncement) : ResponseEntity.notFound().build();
    }    

    //Actualizar anuncio
    @PutMapping("/{id}")
    public ResponseEntity<AnouncementDTO> updateAnouncement(@PathVariable Long id, @RequestBody AnouncementDTO anouncementDTO){
        AnouncementDTO updatedAnouncement = anouncementService.updateAnouncement(id, anouncementDTO);
        if (updatedAnouncement != null) {
            return ResponseEntity.ok(updatedAnouncement);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Crear anuncio 
    @PostMapping
    public ResponseEntity<AnouncementDTO> createAnouncement(@RequestBody AnouncementDTO anouncementDTO){
        AnouncementDTO newAnouncement = anouncementService.createAnouncement(anouncementDTO);
        return ResponseEntity.ok(newAnouncement);
    }

    //Eliminar un anuncio
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteRoom(@PathVariable Long id){
        boolean deleted = anouncementService.deleteAnouncement(id);

        Map<String, String> response = new HashMap<>();

        if(deleted){
            response.put("message", "Anuncio eliminado con exito");
            return ResponseEntity.ok(response);
        }else{
            response.put("message", "Anuncio no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
