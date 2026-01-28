package com.ecommerce.ecommerce_backend.controller.user;

import com.ecommerce.ecommerce_backend.dto.subscription.SubscribePremiumDTO;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.service.user.PremiumSubscriptionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/subscription")
public class UserSubscriptionController {

    private final PremiumSubscriptionService premiumSubscriptionService;
    private final AuthService authService;

    public UserSubscriptionController(
            PremiumSubscriptionService premiumSubscriptionService,
            AuthService authService
    ) {
        this.premiumSubscriptionService = premiumSubscriptionService;
        this.authService = authService;
    }

    @PostMapping("/subscribe")
    public String subscribe(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody SubscribePremiumDTO dto
    ) {
        User user = authService.getUserById(userId);
        premiumSubscriptionService.subscribe(user, dto.getPlanType());
        return "Premium subscription activated successfully";
    }
}
