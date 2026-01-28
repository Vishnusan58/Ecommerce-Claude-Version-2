package com.ecommerce.ecommerce_backend.dto.payment;

public class PaymentSummaryDTO {

    private String paymentMethod;
    private String status;
    private double amount;

    public PaymentSummaryDTO() {}

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
