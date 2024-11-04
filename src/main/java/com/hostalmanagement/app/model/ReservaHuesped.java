package com.hostalmanagement.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ReservaHuesped {
    @Id
    @ManyToOne
    @JoinColumn(name  = "NIFHuesped", referencedColumnName = "NIF")
    private Huesped huesped;

    @Id
    @ManyToOne
    @JoinColumn(name = "idReserva", referencedColumnName = "id")
    private Reserva reserva; 

    public ReservaHuesped() { }

    public ReservaHuesped(Huesped huesped, Reserva reserva) {
        this.huesped = huesped;
        this.reserva = reserva;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public void setHuesped(Huesped huesped) {
        this.huesped = huesped;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

}   
