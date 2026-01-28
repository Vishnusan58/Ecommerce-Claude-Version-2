package com.ecommerce.ecommerce_backend.dto.admin;

public class AdminPayoutDTO {

    private Long payoutId;
    private String sellerEmail;
    private double amount;
    private String status;

    public AdminPayoutDTO() {}

    public Long getPayoutId() { return payoutId; }
    public void setPayoutId(Long payoutId) { this.payoutId = payoutId; }

    public String getSellerEmail() { return sellerEmail; }
    public void setSellerEmail(String sellerEmail) { this.sellerEmail = sellerEmail; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
