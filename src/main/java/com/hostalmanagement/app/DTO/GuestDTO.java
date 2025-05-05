package com.hostalmanagement.app.DTO;

public class GuestDTO {
    private String nif;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private Long tenantId;

    public GuestDTO() {
    }

    public GuestDTO(String nif, String name, String lastname, String email, String phone, Long tenantId) {
        this.nif = nif;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.tenantId = tenantId;
    }


    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
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

}
