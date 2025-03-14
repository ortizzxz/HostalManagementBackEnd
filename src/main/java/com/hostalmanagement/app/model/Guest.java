package com.hostalmanagement.app.model;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Guest {
    @Id
    @NotNull
    private String nif;

    private String name;
    private String lastname;
    private String email;
    private String phone;
    private LocalDateTime registerDate;

    @OneToMany(mappedBy = "guest")
    private Set<GuestReservation> reservations;

    public Guest() {
        this.registerDate = LocalDateTime.now();
    }

    public Guest(String email, String lastname, String name, String nif, String phone) {
        this.email = email;
        this.lastname = lastname;
        this.name = name;
        this.nif = nif;
        this.phone = phone;
        this.registerDate = LocalDateTime.now();
    }

    public String getNIF() {
        return nif;
    }

    public void setNIF(String nif) {
        this.nif = nif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public Set<GuestReservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<GuestReservation> reservations) {
        this.reservations = reservations;
    }


    @Override // as a JSON
    public String toString() {
        return "Guest{ NIF=" + getNIF() + ", Name=" + getName() + ",Lastname=" + getLastname()
                + ", Email=" + getEmail() + ",Phone()=" + getPhone() + ", Register Date="
                + getRegisterDate() + "}";
    }

}
