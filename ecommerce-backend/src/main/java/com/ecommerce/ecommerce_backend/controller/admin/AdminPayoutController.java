package com.ecommerce.ecommerce_backend.controller.admin;

import com.ecommerce.ecommerce_backend.dto.admin.AdminPayoutDTO;
import com.ecommerce.ecommerce_backend.model.SellerPayout;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.admin.AdminPayoutService;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/payouts")
public class AdminPayoutController {

    private final AdminPayoutService payoutService;
    private final AuthService authService;

    public AdminPayoutController(AdminPayoutService payoutService,
                                 AuthService authService) {
        this.payoutService = payoutService;
        this.authService = authService;
    }

    @GetMapping
    public List<AdminPayoutDTO> getAllPayouts(
            @RequestHeader("X-ADMIN-ID") Long adminId) {

        User admin = authService.getUserById(adminId);

        return payoutService.getAllPayouts(admin)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private AdminPayoutDTO mapToDto(SellerPayout payout) {
        AdminPayoutDTO dto = new AdminPayoutDTO();
        dto.setPayoutId(payout.getId());
        dto.setSellerEmail(payout.getSeller().getEmail());
        dto.setAmount(payout.getAmount());
        dto.setStatus(payout.getStatus().name());
        return dto;
    }
}
