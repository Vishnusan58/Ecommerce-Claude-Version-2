package com.ecommerce.ecommerce_backend.dto.coupon;

public class CouponResponseDTO {

    private String code;
    private double discountAmount;
    private boolean applied;

    public CouponResponseDTO() {}

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }

    public boolean isApplied() { return applied; }
    public void setApplied(boolean applied) { this.applied = applied; }
}
