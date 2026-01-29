package com.ecommerce.ecommerce_backend.dto.order;

import java.time.LocalDate;

public class PlaceOrderDTO {

    // Support both addressId (for saved addresses) and inline shippingAddress
    private Long addressId;
    private ShippingAddressDTO shippingAddress;
    private String paymentMethod; // COD, UPI, CARD
    private PaymentDetailsDTO paymentDetails;
    private String couponCode;
    private LocalDate preferredDeliveryDate;

    public PlaceOrderDTO() {}

    // Nested DTO for inline shipping address
    public static class ShippingAddressDTO {
        private String fullName;
        private String phone;
        private String street;
        private String city;
        private String state;
        private String zipCode;
        private String country;

        public ShippingAddressDTO() {}

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getStreet() { return street; }
        public void setStreet(String street) { this.street = street; }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getState() { return state; }
        public void setState(String state) { this.state = state; }

        public String getZipCode() { return zipCode; }
        public void setZipCode(String zipCode) { this.zipCode = zipCode; }

        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
    }

    // Nested DTO for payment details
    public static class PaymentDetailsDTO {
        private String upiId;
        private String cardNumber;
        private String cardExpiry;
        private String cardCvv;
        private String cardHolder;

        public PaymentDetailsDTO() {}

        public String getUpiId() { return upiId; }
        public void setUpiId(String upiId) { this.upiId = upiId; }

        public String getCardNumber() { return cardNumber; }
        public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

        public String getCardExpiry() { return cardExpiry; }
        public void setCardExpiry(String cardExpiry) { this.cardExpiry = cardExpiry; }

        public String getCardCvv() { return cardCvv; }
        public void setCardCvv(String cardCvv) { this.cardCvv = cardCvv; }

        public String getCardHolder() { return cardHolder; }
        public void setCardHolder(String cardHolder) { this.cardHolder = cardHolder; }
    }

    // Getters and Setters
    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }

    public ShippingAddressDTO getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(ShippingAddressDTO shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public PaymentDetailsDTO getPaymentDetails() { return paymentDetails; }
    public void setPaymentDetails(PaymentDetailsDTO paymentDetails) { this.paymentDetails = paymentDetails; }

    public String getCouponCode() { return couponCode; }
    public void setCouponCode(String couponCode) { this.couponCode = couponCode; }

    public LocalDate getPreferredDeliveryDate() { return preferredDeliveryDate; }
    public void setPreferredDeliveryDate(LocalDate preferredDeliveryDate) { this.preferredDeliveryDate = preferredDeliveryDate; }
}
