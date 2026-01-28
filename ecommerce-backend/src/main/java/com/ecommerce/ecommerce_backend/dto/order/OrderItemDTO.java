package com.ecommerce.ecommerce_backend.dto.order;

public class OrderItemDTO {

    private String productName;
    private String sellerName;
    private int quantity;
    private double price;
    private double subtotal;

    public OrderItemDTO() {}

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getSellerName() { return sellerName; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
