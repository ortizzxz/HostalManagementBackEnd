package com.hostalmanagement.app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "reserva", cascade = CascadeType.ALL)
    private CheckInOut checkInOut;

    @OneToOne(mappedBy = "reserva", cascade = CascadeType.ALL)
    private Factura factura;

    // Relación @ManyToOne: Muchas Reservas están asociadas con un Huesped
    @ManyToOne
    @JoinColumn(name = "NIFHuesped")
    private Huesped huesped;

    // Relación @ManyToOne: Muchas Reservas están asociadas con una Habitacion
    @ManyToOne
    @JoinColumn(name = "idHabitacion")
    private Habitacion habitacion;

    @Enumerated(EnumType.STRING)
    private EstadoReserva estado;

    public Reserva() {}

    public Reserva(Huesped huesped, Habitacion habitacion, EstadoReserva estado) {
        this.huesped = huesped;
        this.habitacion = habitacion;
        this.estado = estado;
    }

    private enum EstadoReserva {
        CONFIRMADA, CANCELADA, COMPLETADA
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public void setHuesped(Huesped huesped) {
        this.huesped = huesped;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Reserva{getId=" + getId() + ", Huesped=" + getHuesped() + ", Habitacion=" + getHabitacion()
                + ", Estado=" + getEstado() + "}";
    }

}
