package com.hostalmanagement.app.dao;

import java.util.List;

import com.hostalmanagement.app.model.Inventario;

public interface InventarioDAO {
    Inventario findById(Long id);
    
    List<Inventario> findAll();
    List<Inventario> findByKeyword(String item);
    List<Inventario> findByLowLevel();

    void save(Inventario inventario);
    void update(Inventario inventario);
    void delete(Long id);
}
