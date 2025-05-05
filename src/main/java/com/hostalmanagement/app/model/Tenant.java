package com.hostalmanagement.app.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Optional: Bi-directional mapping (if you want to access users from a tenant)
    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
    private Set<User> users;

    public Tenant() {}

    public Tenant(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Set<User> getUsers() { return users; }

    public void setUsers(Set<User> users) { this.users = users; }

}
