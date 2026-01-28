package com.ecommerce.ecommerce_backend.dto.user;

public class UpdateUserProfileDTO {

    private String name;
    private String phone;

    public UpdateUserProfileDTO() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
