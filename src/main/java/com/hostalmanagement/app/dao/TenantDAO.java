package com.hostalmanagement.app.dao;

import java.util.List;

import com.hostalmanagement.app.model.Tenant;

public interface TenantDAO {
    Tenant findById(Long id);
    List<Tenant> findAll();
    void save(Tenant tenant);
    void update(Tenant tenant);
    void remove(Long id);
}
