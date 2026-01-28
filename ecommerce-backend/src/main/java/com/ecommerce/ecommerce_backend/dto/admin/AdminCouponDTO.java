package com.ecommerce.ecommerce_backend.dto.admin;

public class AdminCouponDTO {

    private Long couponId;
    private String code;
    private String discountType;
    private double discountValue;
    private boolean active;

    public AdminCouponDTO() {}

    public Long getCouponId() { return couponId; }
    public void setCouponId(Long couponId) { this.couponId = couponId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDiscountType() { return discountType; }
    public void setDiscountType(String discountType) { this.discountType = discountType; }

    public double getDiscountValue() { return discountValue; }
    public void setDiscountValue(double discountValue) { this.discountValue = discountValue; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
