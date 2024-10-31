package com.hostalmanagement.app.model;

import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaEmision;
    private double cantidadTotal;
    private EstadoFactura estado;
    
    @OneToOne
    @JoinColumn(name = "idReserva") 
    private Reserva reserva;

    public Factura(){}

    public Factura(LocalDateTime fechaEmision, double cantidadTotal, EstadoFactura estado, Reserva reserva) {
        this.fechaEmision = fechaEmision;
        this.cantidadTotal = cantidadTotal;
        this.estado = estado;
        this.reserva = reserva;
    }

    public enum EstadoFactura{PENDIENTE, PAGADA, CANCELADA}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public double getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(double cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public EstadoFactura getEstado() {
        return estado;
    }

    public void setEstado(EstadoFactura estado) {
        this.estado = estado;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    @Override
    public String toString() {
        return "Factura{Id=" + getId() + ", FechaEmision=" + getFechaEmision() + ", CantidadTotal="
                + getCantidadTotal() + ", Estado=" + getEstado() + ", Reserva=" + getReserva() + "}";
    };

    
}
