package com.ecommerce.ecommerce_backend.dto.returnrefund;

public class ReturnRequestDTO {

    private Long orderItemId;
    private String reason;

    public ReturnRequestDTO() {}

    public Long getOrderItemId() { return orderItemId; }
    public void setOrderItemId(Long orderItemId) { this.orderItemId = orderItemId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
