package com.hostalmanagement.app.DTO;

public class WageDTO {
    private long id;
    private UserDTO userDTO;
    private double hourRate;
    private int weeklyHours;
    private double taxImposed;
    private long extraPayments;

    public WageDTO() {
    }

    public WageDTO(Long id, UserDTO userDTO, double hourRate, int weeklyHours, double taxImposed, long extraPayments) {
        this.id = id;
        this.userDTO = userDTO;
        this.hourRate = hourRate;
        this.weeklyHours = weeklyHours;
        this.taxImposed = taxImposed;
        this.extraPayments = extraPayments;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public int getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public double getTaxImposed() {
        return taxImposed;
    }

    public void setTaxImposed(double taxImposed) {
        this.taxImposed = taxImposed;
    }

    public long getExtraPayments() {
        return extraPayments;
    }

    public void setExtraPayments(long extraPayments) {
        this.extraPayments = extraPayments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getHourRate() {
        return hourRate;
    }

    public void setHourRate(double hourRate) {
        this.hourRate = hourRate;
    }

}
