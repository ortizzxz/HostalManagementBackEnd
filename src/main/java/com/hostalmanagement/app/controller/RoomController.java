package com.hostalmanagement.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hostalmanagement.app.HostalManagementApplication;
import com.hostalmanagement.app.DTO.RoomDTO;
import com.hostalmanagement.app.config.SecurityConfig;
import com.hostalmanagement.app.model.Tenant;
import com.hostalmanagement.app.service.RoomService;
import com.hostalmanagement.app.service.TenantService;


@RestController
@RequestMapping("/api/room")
public class RoomController {

    private final SecurityConfig securityConfig;
    private final HostalManagementApplication hostalManagementApplication;

    @Autowired
    private RoomService roomService;

    @Autowired
    private TenantService tenantService;

    RoomController(HostalManagementApplication hostalManagementApplication, 
                   SecurityConfig securityConfig){
        this.hostalManagementApplication = hostalManagementApplication;
        this.securityConfig = securityConfig;
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> findAllRooms(Long tenantId){
        Tenant tenant = tenantService.findById(tenantId);

        List<RoomDTO> rooms = roomService.findAllRooms(tenant);
        return ResponseEntity.ok(rooms);
    }

    //Buscar una habitación por ID
    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> findRoomById(@PathVariable Long id){
        RoomDTO room = roomService.findRoomById(id);
        return (room != null) ? ResponseEntity.ok(room) : ResponseEntity.notFound().build();
    }

    // Actualizar room 
    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO){
        RoomDTO updatedRoom = roomService.updateRoom(id, roomDTO);
        if (updatedRoom != null) {
            return ResponseEntity.ok(updatedRoom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un nuevo Room
    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO roomDTO) {
        RoomDTO newRoom = roomService.createRoom(roomDTO);
        return ResponseEntity.ok(newRoom);
    }

    // Eliminar una room 
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteRoom(@PathVariable Long id) {
        boolean deleted = roomService.deleteRoom(id);
        
        Map<String, String> response = new HashMap<>();
        
        if (deleted) {
            response.put("message", "Habitación eliminada con éxito");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Habitación no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
