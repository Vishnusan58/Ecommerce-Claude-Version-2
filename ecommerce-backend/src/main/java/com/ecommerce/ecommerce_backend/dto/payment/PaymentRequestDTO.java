package com.ecommerce.ecommerce_backend.dto.payment;

public class PaymentRequestDTO {

    private Long orderId;
    private String paymentMethod;

    public PaymentRequestDTO() {}

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}
