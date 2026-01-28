package com.ecommerce.ecommerce_backend.controller.user;

import com.ecommerce.ecommerce_backend.dto.user.UpdateUserProfileDTO;
import com.ecommerce.ecommerce_backend.dto.user.UserProfileResponseDTO;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.service.user.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/profile")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService,
                          AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping
    public UserProfileResponseDTO getProfile(
            @RequestHeader("X-USER-ID") Long userId) {

        User user = authService.getUserById(userId);

        UserProfileResponseDTO dto = new UserProfileResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole().name());

        return dto;
    }

    @PutMapping
    public UserProfileResponseDTO updateProfile(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody UpdateUserProfileDTO request) {

        User user = authService.getUserById(userId);

        User updated = userService.updateProfile(
                user,
                request.getName(),
                request.getPhone()
        );

        UserProfileResponseDTO dto = new UserProfileResponseDTO();
        dto.setId(updated.getId());
        dto.setEmail(updated.getEmail());
        dto.setName(updated.getName());
        dto.setPhone(updated.getPhone());
        dto.setRole(updated.getRole().name());

        return dto;
    }

    @PutMapping("/request-seller")
    public String requestSellerRole(
            @RequestHeader("X-USER-ID") Long userId) {

        userService.requestSellerRole(userId);
        return "Seller role requested. Await admin approval.";
    }

}
