package com.hostalmanagement.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hostalmanagement.app.HostalManagementApplication;
import com.hostalmanagement.app.DTO.InventoryDTO;
import com.hostalmanagement.app.config.SecurityConfig;
import com.hostalmanagement.app.service.InventoryService;

@RequestMapping("/api/inventory")
public class InventoryController {
    private final SecurityConfig securityConfig;
    private final HostalManagementApplication hostalManagementApplication;

    @Autowired
    InventoryService inventoryService;

    InventoryController(HostalManagementApplication hostalManagementApplication, 
                   SecurityConfig securityConfig){
        this.hostalManagementApplication = hostalManagementApplication;
        this.securityConfig = securityConfig;
    }

    //Obtener todos los inventarios
    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllInventories(){
        List<InventoryDTO> inventories = inventoryService.findAllInventories();
        return ResponseEntity.ok(inventories);
    }
}
