package com.hostalmanagement.app.DTO;

public class TenantDTO {
    private Long id;
    private String name;

    public TenantDTO() {
    }

    public TenantDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and setters
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

    @Override
    public String toString() {
        return "TenantDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
