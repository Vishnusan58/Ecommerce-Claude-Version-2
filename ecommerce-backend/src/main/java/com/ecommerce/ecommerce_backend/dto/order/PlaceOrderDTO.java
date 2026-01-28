package com.ecommerce.ecommerce_backend.dto.order;

import java.time.LocalDate;

public class PlaceOrderDTO {

    private Long addressId;
    private String couponCode;
    private LocalDate preferredDeliveryDate;
    public PlaceOrderDTO() {}

    public LocalDate getPreferredDeliveryDate() {
        return preferredDeliveryDate;
    }

    public void setPreferredDeliveryDate(LocalDate preferredDeliveryDate) {
        this.preferredDeliveryDate = preferredDeliveryDate;
    }

    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }

    public String getCouponCode() { return couponCode; }
    public void setCouponCode(String couponCode) { this.couponCode = couponCode; }
}
