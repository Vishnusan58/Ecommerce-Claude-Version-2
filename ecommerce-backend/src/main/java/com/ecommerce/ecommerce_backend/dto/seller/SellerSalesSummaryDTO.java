package com.ecommerce.ecommerce_backend.dto.seller;

public class SellerSalesSummaryDTO {

    private int totalOrders;
    private double totalRevenue;

    public SellerSalesSummaryDTO() {}

    public int getTotalOrders() { return totalOrders; }
    public void setTotalOrders(int totalOrders) { this.totalOrders = totalOrders; }

    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
}
