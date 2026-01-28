package com.ecommerce.ecommerce_backend.dto.seller;

public class SellerPayoutDTO {

    private Long payoutId;
    private double amount;
    private String status;

    public SellerPayoutDTO() {}

    public Long getPayoutId() { return payoutId; }
    public void setPayoutId(Long payoutId) { this.payoutId = payoutId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
