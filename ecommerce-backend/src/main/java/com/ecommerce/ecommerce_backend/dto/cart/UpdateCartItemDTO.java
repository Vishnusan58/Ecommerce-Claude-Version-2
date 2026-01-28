package com.ecommerce.ecommerce_backend.dto.cart;

public class UpdateCartItemDTO {

    private Long cartItemId;
    private int quantity;

    public UpdateCartItemDTO() {}

    public Long getCartItemId() { return cartItemId; }
    public void setCartItemId(Long cartItemId) { this.cartItemId = cartItemId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
