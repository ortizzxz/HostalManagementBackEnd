package com.hostalmanagement.app.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String item;
    private int amount;
    private int warningLevel;
    private LocalDateTime lastUpdate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    public Inventory() {
    }

    public Inventory(String item, int amount, int warningLevel, LocalDateTime lastUpdate, Tenant tenant) {
        this.item = item;
        this.amount = amount;
        this.warningLevel = warningLevel;
        this.lastUpdate = lastUpdate;
        this.tenant = tenant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getWarningLevel() {
        return warningLevel;
    }

    public void setWarningLevel(int warningLevel) {
        this.warningLevel = warningLevel;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", amount=" + amount +
                ", warningLevel=" + warningLevel +
                ", lastUpdate=" + lastUpdate +
                ", tenant=" + (tenant != null ? tenant.getId() : null) +
                '}';
    }
}
