package com.ecommerce.ecommerce_backend.repository;

import com.ecommerce.ecommerce_backend.model.Product;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByUser(User user);

    boolean existsByUserAndProduct(User user, Product product);
}
