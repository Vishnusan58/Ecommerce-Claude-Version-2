package com.ecommerce.ecommerce_backend.service.user;

import com.ecommerce.ecommerce_backend.enums.SubscriptionPlan;
import com.ecommerce.ecommerce_backend.model.PremiumSubscription;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.PremiumSubscriptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PremiumSubscriptionService {

    private final PremiumSubscriptionRepository premiumSubscriptionRepository;

    public PremiumSubscriptionService(
            PremiumSubscriptionRepository premiumSubscriptionRepository
    ) {
        this.premiumSubscriptionRepository = premiumSubscriptionRepository;
    }

    public void subscribe(User user, SubscriptionPlan planType) {

        // prevent duplicate active subscription
        boolean alreadyActive =
                premiumSubscriptionRepository.existsByUserAndActiveTrue(user);

        if (alreadyActive) {
            throw new RuntimeException("User already has an active premium subscription");
        }

        PremiumSubscription subscription = new PremiumSubscription();
        subscription.setUser(user);
        subscription.setPlanType(planType);
        subscription.setStartDate(LocalDate.now());
        subscription.setActive(true);
        subscription.setAutoRenew(true);

        if (planType == SubscriptionPlan.MONTHLY) {
            subscription.setEndDate(LocalDate.now().plusMonths(1));
        } else {
            subscription.setEndDate(LocalDate.now().plusYears(1));
        }

        premiumSubscriptionRepository.save(subscription);
    }
}
