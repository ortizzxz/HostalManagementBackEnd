package com.hostalmanagement.app.dao;

import com.hostalmanagement.app.model.User;
import com.hostalmanagement.app.model.Wage;

import java.util.List;

public interface WageDAO {
    Wage findById(Long id);
    Wage findByUser(User user);
    List<Wage> findAll();
    List<Wage> findByUserId(Long userId);
    List<Wage> findByTenantId(Long tenantId);

    void save(Wage wage);
    void update(Wage wage);
    void delete(Long id);
}
