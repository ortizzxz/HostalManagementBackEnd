package com.hostalmanagement.app.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String item;
    private int cantidad;
    private int nivelCritico;
    private LocalDateTime ultimaActulizacion;

    public Inventario(){}

    public Inventario(String item, int cantidad, int nivelCritico, LocalDateTime ultimaActulizacion) {
        this.item = item;
        this.cantidad = cantidad;
        this.nivelCritico = nivelCritico;
        this.ultimaActulizacion = ultimaActulizacion;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getNivelCritico() {
        return nivelCritico;
    }

    public void setNivelCritico(int nivelCritico) {
        this.nivelCritico = nivelCritico;
    }

    public LocalDateTime getUltimaActulizacion() {
        return ultimaActulizacion;
    }

    public void setUltimaActulizacion(LocalDateTime ultimaActulizacion) {
        this.ultimaActulizacion = ultimaActulizacion;
    }

    @Override
    public String toString() {
        return "Inventario{Id=" + getId() + ", Item=" + getItem() + ", Cantidad=" + getCantidad()
                + ", NivelCritico=" + getNivelCritico() + ", UltimaActulizacion()=" + getUltimaActulizacion()
                + "}";
    }
    
    
}
