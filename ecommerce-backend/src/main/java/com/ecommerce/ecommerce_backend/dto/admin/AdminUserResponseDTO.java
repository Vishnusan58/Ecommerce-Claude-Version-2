package com.ecommerce.ecommerce_backend.dto.admin;

import java.time.LocalDateTime;

public class AdminUserResponseDTO {

    private Long userId;
    private String email;
    private String role;
    private boolean premium;
    private LocalDateTime createdAt;

    public AdminUserResponseDTO() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
