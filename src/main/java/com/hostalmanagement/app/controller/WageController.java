package com.hostalmanagement.app.controller;

import com.hostalmanagement.app.HostalManagementApplication;
import com.hostalmanagement.app.DTO.WageDTO;
import com.hostalmanagement.app.config.SecurityConfig;
import com.hostalmanagement.app.service.WageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/wages")
@RestController
public class WageController {

    private final SecurityConfig securityConfig;

    private final HostalManagementApplication hostalManagementApplication;

    @Autowired
    private WageService wageService;

    WageController(HostalManagementApplication hostalManagementApplication, SecurityConfig securityConfig) {
        this.hostalManagementApplication = hostalManagementApplication;
        this.securityConfig = securityConfig;
    }

    @GetMapping
    public ResponseEntity<List<WageDTO>> getAllWages(@RequestParam Long tenantId) {
        List<WageDTO> wages = wageService.findWagesByTenantId(tenantId);
        return ResponseEntity.ok(wages);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WageDTO>> getWagesByUserId(@PathVariable Long userId) {
        List<WageDTO> wages = wageService.findWagesByUserId(userId);
        return ResponseEntity.ok(wages);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WageDTO> updateWage(@PathVariable Long id, @RequestBody WageDTO wageDTO) {
        WageDTO updatedWage = wageService.updateWage(id, wageDTO);
        if (updatedWage != null) {
            return ResponseEntity.ok(updatedWage);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<WageDTO> createWage(@RequestBody WageDTO wageDTO) {
        try {
            WageDTO createdWage = wageService.createWage(wageDTO);
            return ResponseEntity.ok(createdWage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
