package com.hostalmanagement.app.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Anouncement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private LocalDateTime postDate;
    private LocalDateTime expirationDate;

    public Anouncement() {
    }

    public Anouncement(String title, String content, LocalDateTime postDate, LocalDateTime expirationDate) {
        this.title = title;
        this.content = content;
        this.postDate = postDate;
        this.expirationDate = expirationDate;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "Anouncement{Id=" + getId() + ", Title=" + getTitle() + ", Content=" + getContent()
                + ", Post Date=" + getPostDate() + ", Expiration Date=" + getExpirationDate()
                + "}";
    }

    
}