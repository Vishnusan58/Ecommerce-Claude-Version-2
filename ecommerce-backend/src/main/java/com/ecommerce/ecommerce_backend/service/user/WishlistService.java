package com.ecommerce.ecommerce_backend.service.user;

import com.ecommerce.ecommerce_backend.model.*;
import com.ecommerce.ecommerce_backend.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public void addToWishlist(User user, Product product) {
        if (wishlistRepository.existsByUserAndProduct(user, product)) {
            return;
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);
        wishlistRepository.save(wishlist);
    }

    public void removeFromWishlist(User user, Product product) {
        wishlistRepository.findByUser(user)
                .stream()
                .filter(w -> w.getProduct().getId().equals(product.getId()))
                .forEach(wishlistRepository::delete);
    }

    public List<Wishlist> getWishlist(User user) {
        return wishlistRepository.findByUser(user);
    }
}
