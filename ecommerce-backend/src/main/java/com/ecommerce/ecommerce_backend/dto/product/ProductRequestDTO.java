package com.ecommerce.ecommerce_backend.dto.product;

public class ProductRequestDTO {

    private String name;
    private String description;
    private double price;
    private Double originalPrice;
    private int stockQuantity;
    private int stock; // Alternative field name from frontend
    private Long categoryId;
    private String imageUrl;
    private String brand;
    private boolean premiumEarlyAccess;

    public ProductRequestDTO() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Double getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(Double originalPrice) { this.originalPrice = originalPrice; }

    public int getStockQuantity() { return stockQuantity > 0 ? stockQuantity : stock; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public boolean isPremiumEarlyAccess() { return premiumEarlyAccess; }
    public void setPremiumEarlyAccess(boolean premiumEarlyAccess) { this.premiumEarlyAccess = premiumEarlyAccess; }
}
