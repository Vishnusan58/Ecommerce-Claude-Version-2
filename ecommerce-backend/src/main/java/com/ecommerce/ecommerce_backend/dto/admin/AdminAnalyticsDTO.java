package com.ecommerce.ecommerce_backend.dto.admin;

public class AdminAnalyticsDTO {

    private long totalUsers;
    private long totalOrders;
    private double totalRevenue;

    public AdminAnalyticsDTO() {}

    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }

    public long getTotalOrders() { return totalOrders; }
    public void setTotalOrders(long totalOrders) { this.totalOrders = totalOrders; }

    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
}
