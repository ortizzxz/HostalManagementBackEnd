package com.hostalmanagement.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.hostalmanagement.app.DTO.InventoryDTO;
import com.hostalmanagement.app.dao.InventoryDAO;
import com.hostalmanagement.app.model.Inventory;

@Service
public class InventoryService {

    @Autowired
    InventoryDAO inventoryDAO;

    // Registrar un inventario
    public InventoryDTO createInventory(InventoryDTO inventoryDTO) {
        try {
            Inventory inventory = toEntity(inventoryDTO);
            inventoryDAO.save(inventory);
            return toDTO(inventory);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error guardando el anuncio: " + e.getMessage());
        }
    }

    // Devuelve todos los inventarios
    public List<InventoryDTO> findAllInventories() {
        List<Inventory> inventories = inventoryDAO.findAll();
        return inventories.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Buscar anuncio por ID
    public InventoryDTO findInventoryById(@PathVariable Long id) {
        Inventory inventory = inventoryDAO.findById(id);
        return (inventory != null) ? toDTO(inventory) : null;
    }

    // Actualizar un inventario
    public InventoryDTO updatInventory(Long id, InventoryDTO inventoryDTO) {
        Inventory existingInventory = inventoryDAO.findById(id);
        if (existingInventory != null) {
            existingInventory.setItem(inventoryDTO.getItem());
            existingInventory.setAmount(inventoryDTO.getAmount());
            existingInventory.setWarningLevel(inventoryDTO.getWarningLevel());
            existingInventory.setLastUpdate(inventoryDTO.getLastUpdate());
            inventoryDAO.update(existingInventory);
            return toDTO(existingInventory);
        }
        return null;
    }

    public boolean DeleteInventory(Long id) {
        Inventory inventory = inventoryDAO.findById(id);
        if(inventory != null){
            inventoryDAO.delete(id);
            return true;
        }
        return false;
    }

    private InventoryDTO toDTO(Inventory inventory) {
        return new InventoryDTO(
                inventory.getId(),
                inventory.getItem(), 
                inventory.getAmount(), 
                inventory.getWarningLevel(), 
                inventory.getLastUpdate());
    }

    private Inventory toEntity(InventoryDTO inventoryDTO){
        return new Inventory(
            inventoryDTO.getItem(),
            inventoryDTO.getAmount(),
            inventoryDTO.getWarningLevel(),
            inventoryDTO.getLastUpdate()
        );
    }
}
