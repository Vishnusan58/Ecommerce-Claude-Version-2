package com.ecommerce.ecommerce_backend.dto.seller;

import java.time.LocalDateTime;

public class SellerOrderDTO {

    private Long id;
    private Long orderItemId;
    private String orderNumber;
    private String productName;
    private int quantity;
    private double price;
    private double total;
    private String status;
    private String orderStatus; // Keep for backward compatibility
    private boolean isPremiumOrder;
    private LocalDateTime createdAt;
    private int itemCount = 1;

    public SellerOrderDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrderItemId() { return orderItemId; }
    public void setOrderItemId(Long orderItemId) { this.orderItemId = orderItemId; }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) {
        this.status = status;
        this.orderStatus = status; // Keep in sync
    }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        this.status = orderStatus; // Keep in sync
    }

    public boolean getIsPremiumOrder() { return isPremiumOrder; }
    public void setIsPremiumOrder(boolean isPremiumOrder) { this.isPremiumOrder = isPremiumOrder; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public int getItemCount() { return itemCount; }
    public void setItemCount(int itemCount) { this.itemCount = itemCount; }
}
