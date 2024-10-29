package com.main.emailverification.models;

public class VerificationRequest {
    private String userId;
    private String eduEmail;

    // Constructors
    public VerificationRequest() {}

    public VerificationRequest(String userId, String eduEmail) {
        this.userId = userId;
        this.eduEmail = eduEmail;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public String getEduEmail() {
        return eduEmail;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEduEmail(String eduEmail) {
        this.eduEmail = eduEmail;
    }
}
