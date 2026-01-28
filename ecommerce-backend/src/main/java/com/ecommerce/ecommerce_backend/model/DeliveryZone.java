package com.ecommerce.ecommerce_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "delivery_zones")
public class DeliveryZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pincode;
    private boolean active;

    public DeliveryZone() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
