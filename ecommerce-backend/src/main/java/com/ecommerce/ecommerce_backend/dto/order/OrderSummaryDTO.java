package com.ecommerce.ecommerce_backend.dto.order;

import java.time.LocalDateTime;

public class OrderSummaryDTO {

    private Long orderId;
    private LocalDateTime orderDate;
    private String status;
    private double finalAmount;

    public OrderSummaryDTO() {}

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getFinalAmount() { return finalAmount; }
    public void setFinalAmount(double finalAmount) { this.finalAmount = finalAmount; }
}
