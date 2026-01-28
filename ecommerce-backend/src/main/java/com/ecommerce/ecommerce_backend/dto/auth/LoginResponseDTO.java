package com.ecommerce.ecommerce_backend.dto.auth;

public class LoginResponseDTO {

    private Long userId;
    private String role;
    private String name;
    private boolean isPremium;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }

    public LoginResponseDTO() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
