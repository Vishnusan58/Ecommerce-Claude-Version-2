package com.ecommerce.ecommerce_backend.dto.payment;

public class PaymentResponseDTO {

    private Long paymentId;
    private String status;
    private String transactionId;

    public PaymentResponseDTO() {}

    public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
}
