package com.hostalmanagement.app.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class CheckInOut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "idReserva") // Define la columna de la llave foránea en la tabla check_in_out
    private Reserva reserva;

    private LocalDateTime fechaIn;
    private LocalDateTime fechaOut;

    public CheckInOut() {}

    public CheckInOut(Long id, Reserva reserva, LocalDateTime fechaIn, LocalDateTime fechaOut) {
        this.id = id;
        this.reserva = reserva;
        this.fechaIn = fechaIn;
        this.fechaOut = fechaOut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public LocalDateTime getFechaIn() {
        return fechaIn;
    }

    public void setFechaIn(LocalDateTime fechaIn) {
        this.fechaIn = fechaIn;
    }

    public LocalDateTime getFechaOut() {
        return fechaOut;
    }

    public void setFechaOut(LocalDateTime fechaOut) {
        this.fechaOut = fechaOut;
    }

    @Override
    public String toString() {
        return "CheckInOut{getId()=" + getId() + ", getReserva()=" + getReserva() + ", getFechaIn()=" + getFechaIn()
                + ", getFechaOut()=" + getFechaOut() + "}";
    }

}
