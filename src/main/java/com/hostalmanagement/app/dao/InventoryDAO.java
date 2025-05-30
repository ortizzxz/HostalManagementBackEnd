package com.hostalmanagement.app.dao;

import java.util.List;

import com.hostalmanagement.app.model.Inventory;

public interface InventoryDAO {
    Inventory findById(Long id);
    
    List<Inventory> findAll(long tenantId);
    List<Inventory> findByKeyword(String item);
    List<Inventory> findByWarningLevel();

    void save(Inventory inventory);
    void update(Inventory inventory);
    void delete(Long id);
}
