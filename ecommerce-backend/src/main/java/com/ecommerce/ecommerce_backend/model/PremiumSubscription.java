package com.ecommerce.ecommerce_backend.model;

import com.ecommerce.ecommerce_backend.enums.SubscriptionPlan;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "premium_subscriptions")
public class PremiumSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    private SubscriptionPlan planType;



    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
    private boolean autoRenew;

    public PremiumSubscription() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public SubscriptionPlan getPlanType() { return planType; }
    public void setPlanType(SubscriptionPlan planType) { this.planType = planType; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public boolean isAutoRenew() { return autoRenew; }
    public void setAutoRenew(boolean autoRenew) { this.autoRenew = autoRenew; }

}
