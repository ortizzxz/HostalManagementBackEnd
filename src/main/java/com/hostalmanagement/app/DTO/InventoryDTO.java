package com.hostalmanagement.app.DTO;

import java.time.LocalDateTime;

public class InventoryDTO {
    private Long id;
    private String item;
    private int amount;
    private int warningLevel;
    private LocalDateTime lastUpdate;

    public InventoryDTO(){}

    public InventoryDTO(Long id, String item, int amount, int warningLevel, LocalDateTime lastUpdate) {
        this.id = id;
        this.item = item;
        this.amount = amount;
        this.warningLevel = warningLevel;
        this.lastUpdate = lastUpdate;
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

    
}
