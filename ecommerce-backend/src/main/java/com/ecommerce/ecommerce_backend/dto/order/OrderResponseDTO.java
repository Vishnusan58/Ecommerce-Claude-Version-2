package com.ecommerce.ecommerce_backend.dto.order;

public class OrderResponseDTO {

    private Long orderId;
    private double totalAmount;
    private String status;

    public OrderResponseDTO() {}

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
