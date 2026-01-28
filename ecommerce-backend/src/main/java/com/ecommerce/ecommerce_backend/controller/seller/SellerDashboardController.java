package com.ecommerce.ecommerce_backend.controller.seller;

import com.ecommerce.ecommerce_backend.dto.seller.SellerSalesSummaryDTO;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.service.seller.SellerDashboardService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/seller/dashboard")
public class SellerDashboardController {

    private final SellerDashboardService dashboardService;
    private final AuthService authService;

    public SellerDashboardController(SellerDashboardService dashboardService,
                                     AuthService authService) {
        this.dashboardService = dashboardService;
        this.authService = authService;
    }

    @GetMapping
    public SellerSalesSummaryDTO getDashboard(
            @RequestHeader("X-SELLER-ID") Long sellerId) {

        User seller = authService.getUserById(sellerId);

        Map<String, Object> summary = dashboardService.getSellerSummary(seller);

        SellerSalesSummaryDTO dto = new SellerSalesSummaryDTO();
        dto.setTotalOrders((int) summary.get("totalOrders"));
        dto.setTotalRevenue((double) summary.get("totalRevenue"));

        return dto;
    }
}
