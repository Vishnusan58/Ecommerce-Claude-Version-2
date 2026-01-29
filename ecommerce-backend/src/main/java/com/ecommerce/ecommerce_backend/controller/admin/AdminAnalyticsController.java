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

        var overview = analyticsService.getDashboardOverview(admin);

        AdminAnalyticsDTO dto = new AdminAnalyticsDTO();
        dto.setTotalUsers(extractLong(overview.get("totalUsers")));
        dto.setTotalOrders(extractLong(overview.get("totalOrders")));
        dto.setPremiumUsers(extractLong(overview.get("premiumUsers")));
        dto.setTotalSellers(extractLong(overview.get("totalSellers")));
        dto.setPendingSellers(extractLong(overview.get("pendingSellers")));
        dto.setTotalRevenue(analyticsService.getTotalRevenue(admin));

        return dto;
    }

    private long extractLong(Object value) {
        if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof Integer) {
            return ((Integer) value).longValue();
        }
        return 0;
    }
}
