package com.ecommerce.ecommerce_backend.controller.user;

import com.ecommerce.ecommerce_backend.dto.recentlyviewed.RecentlyViewedDTO;
import com.ecommerce.ecommerce_backend.dto.recentlyviewed.RecentlyViewedResponseDTO;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.service.user.RecentlyViewedService;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/recently-viewed")
public class RecentlyViewedController {

    private final RecentlyViewedService recentlyViewedService;
    private final AuthService authService;

    public RecentlyViewedController(RecentlyViewedService recentlyViewedService,
                                    AuthService authService) {
        this.recentlyViewedService = recentlyViewedService;
        this.authService = authService;
    }

    @GetMapping
    public RecentlyViewedResponseDTO getRecentlyViewed(
            @RequestHeader("X-USER-ID") Long userId) {

        User user = authService.getUserById(userId);

        RecentlyViewedResponseDTO response = new RecentlyViewedResponseDTO();
        response.setItems(
                recentlyViewedService.getRecentlyViewed(user)
                        .stream()
                        .map(rv -> {
                            RecentlyViewedDTO dto = new RecentlyViewedDTO();
                            dto.setProductId(rv.getProduct().getId());
                            dto.setProductName(rv.getProduct().getName());
                            dto.setPrice(rv.getProduct().getPrice());
                            dto.setViewedAt(rv.getViewedAt());
                            return dto;
                        }).collect(Collectors.toList())
        );
        return response;
    }
}
