package com.ecommerce.ecommerce_backend.dto.wishlist;

import java.util.List;

public class WishlistResponseDTO {

    private List<WishlistItemDTO> items;

    public WishlistResponseDTO() {}

    public List<WishlistItemDTO> getItems() {
        return items;
    }

    public void setItems(List<WishlistItemDTO> items) {
        this.items = items;
    }
}
