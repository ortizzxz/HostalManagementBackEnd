package com.hostalmanagement.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.hostalmanagement.app.DTO.InventoryDTO;
import com.hostalmanagement.app.dao.InventoryDAO;
import com.hostalmanagement.app.dao.TenantDAO;
import com.hostalmanagement.app.model.Inventory;
import com.hostalmanagement.app.model.Tenant;

@Service
public class InventoryService {

    @Autowired
    InventoryDAO inventoryDAO;

    @Autowired
    TenantDAO tenantDAO;

    // Registrar un inventario
    public InventoryDTO createInventory(InventoryDTO inventoryDTO) {
        try {
            Inventory inventory = toEntity(inventoryDTO);
            inventoryDAO.save(inventory);
            return toDTO(inventory);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error guardando el inventario: " + e.getMessage());
        }
    }

    // Devuelve todos los inventarios
    public List<InventoryDTO> findAllInventories() {
        List<Inventory> inventories = inventoryDAO.findAll();
        return inventories.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Buscar inventario por ID
    public InventoryDTO findInventoryById(@PathVariable Long id) {
        Inventory inventory = inventoryDAO.findById(id);
        return (inventory != null) ? toDTO(inventory) : null;
    }

    // Actualizar un inventario
    public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO) {
        Inventory existingInventory = inventoryDAO.findById(id);
        if (existingInventory != null) {
            Tenant tenant = tenantDAO.findById(inventoryDTO.getTenant());  // Get tenant by tenantId from DTO
            if (tenant == null) {
                throw new IllegalArgumentException("Tenant not found: " + inventoryDTO.getTenant());
            }
            existingInventory.setItem(inventoryDTO.getItem());
            existingInventory.setAmount(inventoryDTO.getAmount());
            existingInventory.setWarningLevel(inventoryDTO.getWarningLevel());
            existingInventory.setLastUpdate(inventoryDTO.getLastUpdate());
            existingInventory.setTenant(tenant);  // Set tenant association

            inventoryDAO.update(existingInventory);
            return toDTO(existingInventory);
        }
        return null;
    }

    public boolean deleteInventory(Long id) {
        Inventory inventory = inventoryDAO.findById(id);
        if (inventory != null) {
            inventoryDAO.delete(id);
            return true;
        }
        return false;
    }

    private InventoryDTO toDTO(Inventory inventory) {
        Long tenantId = inventory.getTenant() != null ? inventory.getTenant().getId() : null;
        return new InventoryDTO(
            inventory.getId(),
            inventory.getItem(), 
            inventory.getAmount(), 
            inventory.getWarningLevel(), 
            inventory.getLastUpdate(),
            tenantId  // Include tenantId in DTO
        );
    }

    private Inventory toEntity(InventoryDTO inventoryDTO) {
        Tenant tenant = tenantDAO.findById(inventoryDTO.getTenant());  // Fetch tenant by tenantId
        if (tenant == null) {
            throw new IllegalArgumentException("Tenant not found: " + inventoryDTO.getTenant());
        }
        return new Inventory(
            inventoryDTO.getItem(),
            inventoryDTO.getAmount(),
            inventoryDTO.getWarningLevel(),
            inventoryDTO.getLastUpdate(),
            tenant  // Set the tenant association
        );
    }
}
