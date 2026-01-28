package com.ecommerce.ecommerce_backend.dto.subscription;

import com.ecommerce.ecommerce_backend.enums.SubscriptionPlan;

public class SubscribePremiumDTO {

    private SubscriptionPlan planType;

    public SubscribePremiumDTO() {}

    public SubscriptionPlan getPlanType() {
        return planType;
    }

    public void setPlanType(SubscriptionPlan planType) {
        this.planType = planType;
    }
}
