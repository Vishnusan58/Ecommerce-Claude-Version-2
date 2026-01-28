package com.ecommerce.ecommerce_backend.model;

import com.ecommerce.ecommerce_backend.enums.PayoutStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "seller_payouts")
public class SellerPayout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User seller;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PayoutStatus status;

    public SellerPayout() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getSeller() { return seller; }
    public void setSeller(User seller) { this.seller = seller; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public PayoutStatus getStatus() { return status; }
    public void setStatus(PayoutStatus status) { this.status = status; }
}

