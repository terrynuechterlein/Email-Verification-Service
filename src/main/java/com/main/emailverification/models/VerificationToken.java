package com.main.emailverification.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private String userId;
    private String eduEmail;
    private LocalDateTime expiryDate;

    // Constructors
    public VerificationToken() {}

    public VerificationToken(String token, String userId, String eduEmail, LocalDateTime expiryDate) {
        this.token = token;
        this.userId = userId;
        this.eduEmail = eduEmail;
        this.expiryDate = expiryDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public String getEduEmail() {
        return eduEmail;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEduEmail(String eduEmail) {
        this.eduEmail = eduEmail;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
