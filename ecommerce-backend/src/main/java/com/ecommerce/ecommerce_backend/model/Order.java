package com.ecommerce.ecommerce_backend.model;

import com.ecommerce.ecommerce_backend.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private UserAddress address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private double totalAmount;
    private LocalDateTime orderDate;
    // ADD THESE FIELDS

    private double deliveryCharge;

    private boolean priority;

    private LocalDate preferredDeliveryDate;
    private double finalAmount;

    public double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public LocalDate getPreferredDeliveryDate() {
        return preferredDeliveryDate;
    }

    public void setPreferredDeliveryDate(LocalDate preferredDeliveryDate) {
        this.preferredDeliveryDate = preferredDeliveryDate;
    }

    public Order() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public UserAddress getAddress() { return address; }
    public void setAddress(UserAddress address) { this.address = address; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }
}
