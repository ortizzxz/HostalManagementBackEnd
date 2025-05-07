package com.hostalmanagement.app.DTO;
import com.hostalmanagement.app.model.Room.RoomState;

public class RoomDTO {
    private Long id;
    private int number;
    private String type;
    private int capacity;
    private double baseRate;
    private RoomState state;
    private TenantDTO tenant;

    public RoomDTO(){}

    public RoomDTO(Long id, int number, String type, int capacity, double baseRate, RoomState state, TenantDTO tenant){
        this.id = id;
        this.number = number;
        this.type = type;
        this.capacity = capacity;
        this.baseRate = baseRate;
        this.state = state;
        this.tenant = tenant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(double baseRate) {
        this.baseRate = baseRate;
    }

    public RoomState getState() {
        return state;
    }

    public void setState(RoomState state) {
        this.state = state;
    }

    public TenantDTO getTenantDTO() {
        return tenant;
    }

    public void setTenant(TenantDTO tenantDTO) {
        this.tenant = tenantDTO;
    }

    
}
