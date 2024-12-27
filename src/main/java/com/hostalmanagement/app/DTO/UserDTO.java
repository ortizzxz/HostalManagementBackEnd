package com.hostalmanagement.app.DTO;

public class UserDTO {
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String rol;

    public UserDTO(){}

    public UserDTO(Long id, String name, String lastname, String email, String rol) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    
}
