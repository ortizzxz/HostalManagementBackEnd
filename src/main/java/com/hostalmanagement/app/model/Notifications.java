package com.hostalmanagement.app.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Notifications {

    public enum EventType {
        INSERT, UPDATE, DELETE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tableName;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private Long recordId;

    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    public Notifications() {
        this.createdAt = LocalDateTime.now();
    }

    public Notifications(String tableName, EventType eventType, Long recordId, Tenant tenant) {
        this.tableName = tableName;
        this.eventType = eventType;
        this.recordId = recordId;
        this.createdAt = LocalDateTime.now();
        this.tenant = tenant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", tableName='" + tableName + '\'' +
                ", eventType=" + eventType +
                ", recordId=" + recordId +
                ", createdAt=" + createdAt +
                ", tenant=" + (tenant != null ? tenant.getId() : null) +
                '}';
    }
}
