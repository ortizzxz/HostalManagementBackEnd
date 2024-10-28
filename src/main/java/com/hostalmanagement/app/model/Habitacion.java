package com.hostalmanagement.app.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numero;
    private String tipo;
    private int capacidad;
    private double tarifaBase;

    @Enumerated(EnumType.STRING)
    private EstadoHabitacion estado;

    @OneToMany(mappedBy = "habitacion")
    private List<Reserva> reservas;

    public enum EstadoHabitacion {
        DISPONIBLE,
        OCUPADO,
        EN_LIMPIEZA,
        MANTENIMIENTO
    };

    public Habitacion() {
    }

    public Habitacion(int numero, String tipo, int capacidad, double tarifaBase, EstadoHabitacion estado) {
        this.numero = numero;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.tarifaBase = tarifaBase;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Habitacion{Id=" + getId() + ", Numero=" + getNumero() + ", Tipo=" + getTipo()
                + ", Capacidad=" + getCapacidad() + ", TarifaBase=" + getTarifaBase() + ", Estado="
                + getEstado() + "}";
    }

}

/*
 * CREATE TABLE habitaciones (
 * id INT PRIMARY KEY AUTO_INCREMENT,
 * numero VARCHAR(10),
 * tipo VARCHAR(50),
 * capacidad INT,
 * tarifaBase DECIMAL(10, 2),
 * estado ENUM('disponible', 'ocupada', 'en_limpieza', 'mantenimiento')
 */
