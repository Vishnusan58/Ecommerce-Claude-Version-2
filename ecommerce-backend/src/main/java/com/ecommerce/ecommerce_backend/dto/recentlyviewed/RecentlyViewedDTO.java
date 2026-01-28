package com.ecommerce.ecommerce_backend.dto.recentlyviewed;

import java.time.LocalDateTime;

public class RecentlyViewedDTO {

    private Long productId;
    private String productName;
    private double price;
    private LocalDateTime viewedAt;

    public RecentlyViewedDTO() {}

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public LocalDateTime getViewedAt() { return viewedAt; }
    public void setViewedAt(LocalDateTime viewedAt) { this.viewedAt = viewedAt; }
}
