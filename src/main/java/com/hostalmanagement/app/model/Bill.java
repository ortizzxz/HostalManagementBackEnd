package com.hostalmanagement.app.model;

import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime issueDate;
    private double totalAmount;
    private BillState state;
    
    @OneToOne
    @JoinColumn(name = "idReserva") 
    private Reservation reservation;

    public Bill(){}
    
    public Bill(LocalDateTime issueDate, double totalAmount, BillState state, Reservation reservation) {
        this.issueDate = issueDate;
        this.totalAmount = totalAmount;
        this.state = state;
        this.reservation = reservation;
    }

    public enum BillState{PENDIENTE, PAGADA, CANCELADA}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BillState getState() {
        return state;
    }

    public void setState(BillState state) {
        this.state = state;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public String toString() {
        return "Factura{Id=" + getId() + ", Issue Date=" + getIssueDate() + ", Amount="
                + getTotalAmount() + ", State=" + getState() + ", Reservation=" + getReservation() + "}";
    };

    
}
