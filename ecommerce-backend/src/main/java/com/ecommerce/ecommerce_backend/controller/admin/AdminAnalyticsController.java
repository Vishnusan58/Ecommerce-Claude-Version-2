package com.ecommerce.ecommerce_backend.controller.admin;

import com.ecommerce.ecommerce_backend.dto.admin.AdminAnalyticsDTO;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.admin.AdminAnalyticsService;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/analytics")
public class AdminAnalyticsController {

    private final AdminAnalyticsService analyticsService;
    private final AuthService authService;

    public AdminAnalyticsController(AdminAnalyticsService analyticsService,
                                    AuthService authService) {
        this.analyticsService = analyticsService;
        this.authService = authService;
    }

    @GetMapping
    public AdminAnalyticsDTO getDashboard(
            @RequestHeader("X-ADMIN-ID") Long adminId) {

        User admin = authService.getUserById(adminId);

        AdminAnalyticsDTO dto = new AdminAnalyticsDTO();
        dto.setTotalUsers(analyticsService.getDashboardOverview(admin)
                .get("totalUsers") instanceof Long
                ? (Long) analyticsService.getDashboardOverview(admin).get("totalUsers")
                : 0);

        dto.setTotalOrders(analyticsService.getDashboardOverview(admin)
                .get("totalOrders") instanceof Long
                ? (Long) analyticsService.getDashboardOverview(admin).get("totalOrders")
                : 0);

        dto.setTotalRevenue(analyticsService.getTotalRevenue(admin));
        return dto;
    }
}
