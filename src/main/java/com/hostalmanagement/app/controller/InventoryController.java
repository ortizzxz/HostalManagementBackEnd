package com.hostalmanagement.app.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hostalmanagement.app.HostalManagementApplication;
import com.hostalmanagement.app.DTO.AnouncementDTO;
import com.hostalmanagement.app.DTO.InventoryDTO;
import com.hostalmanagement.app.config.SecurityConfig;
import com.hostalmanagement.app.service.AnouncementService;
import com.hostalmanagement.app.service.InventoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final SecurityFilterChain securityFilterChain;

    private final SecurityConfig securityConfig;

    private final HostalManagementApplication hostalManagementApplication;

    @Autowired
    private InventoryService inventoryService;

    InventoryController(SecurityConfig securityConfig,
            HostalManagementApplication hostalManagementApplication, SecurityFilterChain securityFilterChain) {
        this.hostalManagementApplication = hostalManagementApplication;
        this.securityConfig = securityConfig;
        this.securityFilterChain = securityFilterChain;
    }

    // Buscar todas los anuncions
    @GetMapping
    public ResponseEntity<List<InventoryDTO>> findAllInventories(Long tenantId) {
        List<InventoryDTO> inventories = inventoryService.findAllInventories(tenantId);
        return ResponseEntity.ok(inventories);
    }

    @PostMapping
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO inventoryDTO) {
        InventoryDTO newInventory = inventoryService.createInventory(inventoryDTO);
        return ResponseEntity.ok(newInventory);
    }

        //Actualizar anuncio
    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable Long id, @RequestBody InventoryDTO inventoryDTO){
        InventoryDTO updatedInventory = inventoryService.updateInventory(id, inventoryDTO);
        if (updatedInventory != null) {
            return ResponseEntity.ok(updatedInventory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
