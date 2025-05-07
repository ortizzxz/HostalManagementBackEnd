package com.hostalmanagement.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostalmanagement.app.DTO.TenantDTO;
import com.hostalmanagement.app.dao.TenantDAO;
import com.hostalmanagement.app.model.Tenant;

@Service
public class TenantService {

    @Autowired
    TenantDAO tenantDAO;

    public Tenant findById(Long tenantId) {
        Tenant tenant = tenantDAO.findById(tenantId);
        return tenant;
    }

    public TenantDTO toDTO(Tenant tenant) {
        return new TenantDTO(tenant.getId(), tenant.getName());
    }

    public Tenant toEntity(TenantDTO tenantDTO) {
        Tenant tenant = new Tenant();
        tenant.setId(tenantDTO.getId());
        tenant.setName(tenantDTO.getName());
        return tenant;
    }
}
