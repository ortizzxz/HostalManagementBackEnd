package com.hostalmanagement.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Wage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Positive(message = "La tarifa por hora debe ser mayor a cero")
    private double hourRate;

    @Positive(message = "Las horas semanales deben ser mayores a cero")
    private int weeklyHours;

    @DecimalMin(value = "0.0", inclusive = true, message = "El impuesto debe ser cero o positivo")
    private double taxImposed;

    @PositiveOrZero(message = "Los pagos extra no pueden ser negativos")
    private long extraPayments;

    public Wage() {}

    public Wage(User user, double hourRate,
            int weeklyHours,
            double taxImposed,
            long extraPayments) {
        this.user = user;
        this.hourRate = hourRate;
        this.weeklyHours = weeklyHours;
        this.taxImposed = taxImposed;
        this.extraPayments = extraPayments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getHourRate() {
        return hourRate;
    }

    public void setHourRate(double hourRate) {
        this.hourRate = hourRate;
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

    public double getMonthlyWage() {
        double taxRate = taxImposed / 100.0;
        double weeklyGross = hourRate * weeklyHours;
        double weeklyNet = weeklyGross * (1 - taxRate);
        return weeklyNet * 4.33 + extraPayments;
    }

    public double getWageByWorkedDays(int days) {
        if (days <= 0)
            return 0;

        double taxRate = taxImposed / 100.0;

        double weeklyGross = hourRate * weeklyHours;
        double weeklyNet = weeklyGross * (1 - taxRate);
        double dailyNet = weeklyNet / 7.0;
        double dailyExtra = extraPayments / 7.0;

        return (dailyNet + dailyExtra) * days;
    }

}
