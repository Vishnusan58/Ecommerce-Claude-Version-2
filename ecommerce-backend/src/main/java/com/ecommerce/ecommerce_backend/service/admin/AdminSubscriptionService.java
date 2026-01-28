package com.ecommerce.ecommerce_backend.service.admin;

import com.ecommerce.ecommerce_backend.model.PremiumSubscription;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.PremiumSubscriptionRepository;
import com.ecommerce.ecommerce_backend.util.AdminAuthUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminSubscriptionService {

    private final PremiumSubscriptionRepository subscriptionRepository;
    private final AdminAuthUtil adminAuthUtil;
    public AdminSubscriptionService(PremiumSubscriptionRepository subscriptionRepository, AdminAuthUtil adminAuthUtil) {
        this.subscriptionRepository = subscriptionRepository;
        this.adminAuthUtil = adminAuthUtil;
    }

    public List<PremiumSubscription> getAllSubscriptions(User admin) {
        adminAuthUtil.validateAdmin(admin);
        return subscriptionRepository.findAll();
    }

    public void cancelSubscription(User admin, Long subscriptionId) {
        adminAuthUtil.validateAdmin(admin);

        PremiumSubscription sub = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        sub.setActive(false);
        subscriptionRepository.save(sub);
    }
}
