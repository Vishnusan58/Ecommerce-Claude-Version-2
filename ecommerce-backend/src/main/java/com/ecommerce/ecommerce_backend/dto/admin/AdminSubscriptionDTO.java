package com.ecommerce.ecommerce_backend.dto.admin;

import java.time.LocalDate;

public class AdminSubscriptionDTO {

    private Long subscriptionId;
    private String userEmail;
    private String planType;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    public AdminSubscriptionDTO() {}

    public Long getSubscriptionId() { return subscriptionId; }
    public void setSubscriptionId(Long subscriptionId) { this.subscriptionId = subscriptionId; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getPlanType() { return planType; }
    public void setPlanType(String planType) { this.planType = planType; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
