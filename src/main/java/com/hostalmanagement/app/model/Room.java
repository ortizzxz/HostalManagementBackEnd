package com.hostalmanagement.app.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;
    private String type;
    private int capacity;
    private double baseRate;

    @Enumerated(EnumType.STRING)
    private RoomState state;

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservations;

    @ManyToOne(optional = false) // each room must belong to a tenant
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    public Room() {
    }

    public Room(int number, String type, int capacity, double baseRate, RoomState state, Tenant tenant) {
        this.number = number;
        this.type = type;
        this.capacity = capacity;
        this.baseRate = baseRate;
        this.state = state;
        this.tenant = tenant;
    }

    public enum RoomState {
        DISPONIBLE,
        OCUPADO,
        EN_LIMPIEZA,
        MANTENIMIENTO
    };

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

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        return "Room{Id=" + getId() + ", Number=" + getNumber() + ", Type=" + getType()
                + ", Capacity=" + getCapacity() + ", baseRate=" + getBaseRate() + ", State="
                + getState() + ", Tenant=" + getTenant() + "}";
    }
}
