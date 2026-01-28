package com.ecommerce.ecommerce_backend.repository;

import com.ecommerce.ecommerce_backend.model.Cart;
import com.ecommerce.ecommerce_backend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart(Cart cart);

    void deleteByCart(Cart cart);
}
