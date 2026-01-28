package com.ecommerce.ecommerce_backend.controller.seller;

import com.ecommerce.ecommerce_backend.dto.seller.SellerPayoutDTO;
import com.ecommerce.ecommerce_backend.model.SellerPayout;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.service.seller.SellerPayoutService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/seller/payouts")
public class SellerPayoutController {

    private final SellerPayoutService payoutService;
    private final AuthService authService;

    public SellerPayoutController(SellerPayoutService payoutService,
                                  AuthService authService) {
        this.payoutService = payoutService;
        this.authService = authService;
    }

    @GetMapping
    public List<SellerPayoutDTO> getPayouts(
            @RequestHeader("X-SELLER-ID") Long sellerId) {

        User seller = authService.getUserById(sellerId);

        return payoutService.getSellerPayouts(seller)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/request")
    public SellerPayoutDTO requestPayout(
            @RequestHeader("X-SELLER-ID") Long sellerId,
            @RequestParam double amount) {

        User seller = authService.getUserById(sellerId);
        SellerPayout payout = payoutService.requestPayout(seller, amount);

        return mapToDto(payout);
    }

    private SellerPayoutDTO mapToDto(SellerPayout payout) {
        SellerPayoutDTO dto = new SellerPayoutDTO();
        dto.setPayoutId(payout.getId());
        dto.setAmount(payout.getAmount());
        dto.setStatus(payout.getStatus().name());
        return dto;
    }
}
