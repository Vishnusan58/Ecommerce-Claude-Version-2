package com.ecommerce.ecommerce_backend.dto.seller;

public class SellerOrderDTO {

    private Long orderItemId;
    private String productName;
    private int quantity;
    private double price;
    private String orderStatus;

    public SellerOrderDTO() {}

    public Long getOrderItemId() { return orderItemId; }
    public void setOrderItemId(Long orderItemId) { this.orderItemId = orderItemId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
}
