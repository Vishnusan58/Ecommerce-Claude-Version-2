package com.ecommerce.ecommerce_backend.dto.address;

public class AddressSummaryDTO {

    private Long id;
    private String city;
    private String state;
    private String pincode;

    public AddressSummaryDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }
}
