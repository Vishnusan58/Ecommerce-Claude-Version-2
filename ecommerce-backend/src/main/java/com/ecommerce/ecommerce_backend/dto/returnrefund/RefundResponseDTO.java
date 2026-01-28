package com.ecommerce.ecommerce_backend.dto.returnrefund;

public class RefundResponseDTO {

    private double refundAmount;
    private String refundStatus;

    public RefundResponseDTO() {}

    public double getRefundAmount() { return refundAmount; }
    public void setRefundAmount(double refundAmount) { this.refundAmount = refundAmount; }

    public String getRefundStatus() { return refundStatus; }
    public void setRefundStatus(String refundStatus) { this.refundStatus = refundStatus; }
}
