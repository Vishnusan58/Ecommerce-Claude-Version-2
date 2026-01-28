package com.ecommerce.ecommerce_backend.dto.order;

import java.util.List;

public class MultiSellerOrderDTO {

    private Long sellerId;
    private String sellerName;
    private List<OrderItemDTO> items;
    private double sellerTotal;

    public MultiSellerOrderDTO() {}

    public Long getSellerId() { return sellerId; }
    public void setSellerId(Long sellerId) { this.sellerId = sellerId; }

    public String getSellerName() { return sellerName; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }

    public List<OrderItemDTO> getItems() { return items; }
    public void setItems(List<OrderItemDTO> items) { this.items = items; }

    public double getSellerTotal() { return sellerTotal; }
    public void setSellerTotal(double sellerTotal) { this.sellerTotal = sellerTotal; }
}
