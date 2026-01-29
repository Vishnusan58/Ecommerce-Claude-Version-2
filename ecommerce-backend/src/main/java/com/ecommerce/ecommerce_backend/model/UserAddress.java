package com.ecommerce.ecommerce_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_addresses")
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String phone;
    private String addressLine1; // Maps to street
    private String addressLine2;
    private String city;
    private String state;
    private String pincode; // Maps to zipCode
    private String country;
    private boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserAddress() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }

    // Helper for consistency with frontend 'street'
    public void setStreet(String street) { this.addressLine1 = street; }
    public String getStreet() { return addressLine1; }

    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    // Helper for consistency with frontend 'zipCode'
    public void setZipCode(String zipCode) { this.pincode = zipCode; }
    public String getZipCode() { return pincode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean aDefault) { isDefault = aDefault; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
