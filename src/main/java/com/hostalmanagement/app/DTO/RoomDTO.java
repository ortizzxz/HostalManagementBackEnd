package com.hostalmanagement.app.DTO;
import com.hostalmanagement.app.model.Room.RoomState;

public class RoomDTO {
    private Long id;
    private int number;
    private String type;
    private int capacity;
    private double baseRate;
    private RoomState state;
    private TenantDTO tenantDTO;

    public RoomDTO(){}

    public RoomDTO(Long id, int number, String type, int capacity, double baseRate, RoomState state, TenantDTO tenantDTO){
        this.id = id;
        this.number = number;
        this.type = type;
        this.capacity = capacity;
        this.baseRate = baseRate;
        this.state = state;
        this.tenantDTO = tenantDTO;
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
        return tenantDTO;
    }

    public void setTenantDTO(TenantDTO tenantDTO) {
        this.tenantDTO = tenantDTO;
    }

    
}
