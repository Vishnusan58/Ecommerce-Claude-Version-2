package com.ecommerce.ecommerce_backend.dto.admin;

public class AdminOrderResponseDTO {

    private Long orderId;
    private String userEmail;
    private String status;
    private double finalAmount;

    public AdminOrderResponseDTO() {}

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getFinalAmount() { return finalAmount; }
    public void setFinalAmount(double finalAmount) { this.finalAmount = finalAmount; }
}
