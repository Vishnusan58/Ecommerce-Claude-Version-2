package com.ecommerce.ecommerce_backend.dto.order;

import java.time.LocalDateTime;

public class OrderResponseDTO {

    private Long id; // Frontend expects 'id', not 'orderId'
    private Long orderId; // Keep for backward compatibility
    private String orderNumber;
    private String status;
    private String paymentStatus;
    private String paymentMethod;
    private double subtotal;
    private double tax;
    private double deliveryFee;
    private double discount;
    private double total;
    private double totalAmount; // Keep for backward compatibility
    private boolean isPremiumOrder;
    private LocalDateTime createdAt;

    public OrderResponseDTO() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) {
        this.id = id;
        this.orderId = id; // Keep both in sync
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
        this.id = orderId; // Keep both in sync
    }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getTax() { return tax; }
    public void setTax(double tax) { this.tax = tax; }

    public double getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(double deliveryFee) { this.deliveryFee = deliveryFee; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public double getTotal() { return total; }
    public void setTotal(double total) {
        this.total = total;
        this.totalAmount = total; // Keep both in sync
    }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
        this.total = totalAmount; // Keep both in sync
    }

    public boolean isPremiumOrder() { return isPremiumOrder; }
    public void setIsPremiumOrder(boolean isPremiumOrder) { this.isPremiumOrder = isPremiumOrder; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
