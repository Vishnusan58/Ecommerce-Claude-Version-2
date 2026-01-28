package com.ecommerce.ecommerce_backend.dto.admin;

public class AdminDeliveryZoneDTO {

    private Long zoneId;
    private String pincode;
    private boolean active;

    public AdminDeliveryZoneDTO() {}

    public Long getZoneId() { return zoneId; }
    public void setZoneId(Long zoneId) { this.zoneId = zoneId; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
