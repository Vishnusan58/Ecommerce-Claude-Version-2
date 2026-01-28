package com.ecommerce.ecommerce_backend.dto.product;

public class ProductSearchDTO {

    private String keyword;
    private Double minPrice;
    private Double maxPrice;
    private Long categoryId;
    private Double minRating;

    public ProductSearchDTO() {}

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }

    public Double getMinPrice() { return minPrice; }
    public void setMinPrice(Double minPrice) { this.minPrice = minPrice; }

    public Double getMaxPrice() { return maxPrice; }
    public void setMaxPrice(Double maxPrice) { this.maxPrice = maxPrice; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public Double getMinRating() { return minRating; }
    public void setMinRating(Double minRating) { this.minRating = minRating; }
}
