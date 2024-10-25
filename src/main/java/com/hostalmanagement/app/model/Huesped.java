package com.hostalmanagement.app.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Huesped {
    @Id
    @NotNull
    private String nif;

    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private LocalDateTime fechaRegistro;

    @OneToMany(mappedBy = "huesped")
    private List<Reserva> reservas;

    public Huesped() {
        this.fechaRegistro = LocalDateTime.now();
    }

    public Huesped(@NotNull String nif, String nombre, String apellidos, String email, String telefono) {
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.fechaRegistro = LocalDateTime.now(); // Establecer la fecha y hora actuales
    }

    public String getNIF() {
        return nif;
    }

    public void setNIF(String nif) {
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override // as a JSON
    public String toString() {
        return "Huesped{ NIF=" + getNIF() + ", Nombre=" + getNombre() + ",Apellidos=" + getApellidos()
                + ", Email=" + getEmail() + ",Telefono()=" + getTelefono() + ", FechaRegistro="
                + getFechaRegistro() + "}";
    }

}
