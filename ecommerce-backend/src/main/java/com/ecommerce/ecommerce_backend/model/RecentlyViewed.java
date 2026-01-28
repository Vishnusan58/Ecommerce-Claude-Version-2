package com.ecommerce.ecommerce_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recently_viewed")
public class RecentlyViewed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    private LocalDateTime viewedAt;

    public RecentlyViewed() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public LocalDateTime getViewedAt() { return viewedAt; }
    public void setViewedAt(LocalDateTime viewedAt) { this.viewedAt = viewedAt; }
}

