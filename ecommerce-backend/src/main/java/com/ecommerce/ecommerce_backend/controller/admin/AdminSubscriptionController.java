package com.ecommerce.ecommerce_backend.controller.admin;

import com.ecommerce.ecommerce_backend.dto.admin.AdminSubscriptionDTO;
import com.ecommerce.ecommerce_backend.model.PremiumSubscription;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.admin.AdminSubscriptionService;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/subscriptions")
public class AdminSubscriptionController {

    private final AdminSubscriptionService subscriptionService;
    private final AuthService authService;

    public AdminSubscriptionController(AdminSubscriptionService subscriptionService,
                                       AuthService authService) {
        this.subscriptionService = subscriptionService;
        this.authService = authService;
    }

    @GetMapping
    public List<AdminSubscriptionDTO> getAllSubscriptions(
            @RequestHeader("X-ADMIN-ID") Long adminId) {

        User admin = authService.getUserById(adminId);

        return subscriptionService.getAllSubscriptions(admin)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{subscriptionId}")
    public void cancelSubscription(
            @RequestHeader("X-ADMIN-ID") Long adminId,
            @PathVariable Long subscriptionId) {

        User admin = authService.getUserById(adminId);
        subscriptionService.cancelSubscription(admin, subscriptionId);
    }

    private AdminSubscriptionDTO mapToDto(PremiumSubscription sub) {
        AdminSubscriptionDTO dto = new AdminSubscriptionDTO();
        dto.setSubscriptionId(sub.getId());
        dto.setUserEmail(sub.getUser().getEmail());
        dto.setPlanType(sub.getPlanType().name());
        dto.setStartDate(sub.getStartDate());
        dto.setEndDate(sub.getEndDate());
        dto.setActive(sub.isActive());
        return dto;
    }
}
