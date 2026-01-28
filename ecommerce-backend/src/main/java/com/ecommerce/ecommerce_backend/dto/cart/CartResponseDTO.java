package com.ecommerce.ecommerce_backend.dto.cart;

import java.util.List;

public class CartResponseDTO {

    private List<CartItemDTO> items;
    private double totalAmount;
    private double discountAmount;
    private double finalAmount;

    public CartResponseDTO() {}

    public List<CartItemDTO> getItems() { return items; }
    public void setItems(List<CartItemDTO> items) { this.items = items; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }

    public double getFinalAmount() { return finalAmount; }
    public void setFinalAmount(double finalAmount) { this.finalAmount = finalAmount; }
}
