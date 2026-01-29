package com.ecommerce.ecommerce_backend.dto.admin;

public class AdminAnalyticsDTO {

    private long totalUsers;
    private long totalOrders;
    private double totalRevenue;
    private long premiumUsers;
    private long totalSellers;
    private long pendingSellers;

    public AdminAnalyticsDTO() {}

    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }

    public long getTotalOrders() { return totalOrders; }
    public void setTotalOrders(long totalOrders) { this.totalOrders = totalOrders; }

    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }

    public long getPremiumUsers() { return premiumUsers; }
    public void setPremiumUsers(long premiumUsers) { this.premiumUsers = premiumUsers; }

    public long getTotalSellers() { return totalSellers; }
    public void setTotalSellers(long totalSellers) { this.totalSellers = totalSellers; }

    public long getPendingSellers() { return pendingSellers; }
    public void setPendingSellers(long pendingSellers) { this.pendingSellers = pendingSellers; }
}
