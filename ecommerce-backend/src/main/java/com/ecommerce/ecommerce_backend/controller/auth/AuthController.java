package com.ecommerce.ecommerce_backend.controller.auth;

import com.ecommerce.ecommerce_backend.dto.auth.LoginRequestDTO;
import com.ecommerce.ecommerce_backend.dto.auth.LoginResponseDTO;
import com.ecommerce.ecommerce_backend.dto.auth.RegisterRequestDTO;
import com.ecommerce.ecommerce_backend.enums.UserRole;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.PremiumSubscriptionRepository;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final PremiumSubscriptionRepository premiumSubscriptionRepository;
    public AuthController(AuthService authService, PremiumSubscriptionRepository premiumSubscriptionRepository) {
        this.authService = authService;
        this.premiumSubscriptionRepository = premiumSubscriptionRepository;
    }

    // =========================
    // REGISTER
    // =========================
    @PostMapping("/register")
    public LoginResponseDTO register(@RequestBody RegisterRequestDTO request) {

        User user = authService.registerUser(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getPhone(),
                UserRole.valueOf("customer".toUpperCase())
        );

        LoginResponseDTO response = new LoginResponseDTO();
        response.setUserId(user.getId());
        response.setRole(user.getRole().name());
        response.setName(user.getName());

        response.setPremium(premiumSubscriptionRepository.existsByUserAndActiveTrue(user));


        return response;
    }

    // =========================
    // LOGIN
    // =========================
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {

        User user = authService.loginUser(
                request.getEmail(),
                request.getPassword()
        );

        LoginResponseDTO response = new LoginResponseDTO();
        response.setUserId(user.getId());
        response.setRole(user.getRole().name());
        response.setName(user.getName());

        response.setPremium(premiumSubscriptionRepository.existsByUserAndActiveTrue(user));

        return response;
    }
}
