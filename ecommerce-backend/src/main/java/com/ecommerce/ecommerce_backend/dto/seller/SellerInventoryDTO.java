package com.ecommerce.ecommerce_backend.dto.seller;

public class SellerInventoryDTO {

    private Long productId;
    private String productName;
    private int stockQuantity;

    public SellerInventoryDTO() {}

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
}
