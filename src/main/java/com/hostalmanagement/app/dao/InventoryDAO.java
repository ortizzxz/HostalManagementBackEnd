package com.hostalmanagement.app.dao;

import java.util.List;

import com.hostalmanagement.app.model.Inventory;

public interface InventoryDAO {
    Inventario findById(Long id);
    
    List<Inventario> findAll();
    List<Inventario> findByKeyword(String item);
    List<Inventario> findByLowLevel();

    void save(Inventory inventory);
    void update(Inventory inventory);
    void delete(Long id);
}
