package com.ecommerce.ecommerce_backend.controller.admin;

import com.ecommerce.ecommerce_backend.dto.admin.AdminUserResponseDTO;
import com.ecommerce.ecommerce_backend.enums.UserRole;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.admin.AdminUserService;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.util.AdminAuthUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;
    private final AuthService authService;
    private final AdminAuthUtil adminAuthUtil;


    public AdminUserController(AdminUserService adminUserService,
                               AuthService authService, AdminAuthUtil adminAuthUtil) {
        this.adminUserService = adminUserService;
        this.authService = authService;
        this.adminAuthUtil = adminAuthUtil;
    }

    @GetMapping
    public Page<AdminUserResponseDTO> getAllUsers(
            @RequestHeader("X-ADMIN-ID") Long adminId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String role) {

        User admin = authService.getUserById(adminId);

        List<User> allUsers = adminUserService.getAllUsers(admin);

        // Filter by role if provided
        if (role != null && !role.isEmpty()) {
            allUsers = allUsers.stream()
                    .filter(u -> u.getRole().name().equals(role))
                    .collect(Collectors.toList());
        }

        // Convert to DTOs
        List<AdminUserResponseDTO> dtos = allUsers.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        // Manual pagination
        int start = page * size;
        int end = Math.min(start + size, dtos.size());
        List<AdminUserResponseDTO> pageContent = start < dtos.size()
                ? dtos.subList(start, end)
                : List.of();

        return new PageImpl<>(pageContent, PageRequest.of(page, size), dtos.size());
    }

    @PutMapping("/{userId}/role")
    public Map<String, String> changeUserRole(
            @RequestHeader("X-ADMIN-ID") Long adminId,
            @PathVariable Long userId,
            @RequestBody Map<String, String> body) {

        User admin = authService.getUserById(adminId);
        String roleStr = body.get("role");
        UserRole role = UserRole.valueOf(roleStr);
        adminUserService.changeUserRole(admin, userId, role);
        return Map.of("message", "Role updated successfully");
    }

    private AdminUserResponseDTO mapToDto(User user) {
        AdminUserResponseDTO dto = new AdminUserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
        dto.setPremiumStatus(user.isPremiumStatus());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    @GetMapping("/sellers/pending")
    public List<User> getPendingSellers(
            @RequestHeader("X-ADMIN-ID") Long adminId) {
        User user =
        adminAuthUtil.validateAdmin(adminId);
        return adminUserService.getPendingSellers();
    }

    @PostMapping("/sellers/{sellerId}/approve")
    public java.util.Map<String, String> approveSeller(
            @PathVariable Long sellerId,
            @RequestHeader("X-ADMIN-ID") Long adminId) {

        adminAuthUtil.validateAdmin(adminId);
        adminUserService.approveSeller(sellerId);
        return java.util.Map.of("message", "Seller approved successfully");
    }

    @PostMapping("/sellers/{sellerId}/reject")
    public java.util.Map<String, String> rejectSeller(
            @PathVariable Long sellerId,
            @RequestHeader("X-ADMIN-ID") Long adminId) {

        adminAuthUtil.validateAdmin(adminId);
        adminUserService.rejectSeller(sellerId);
        return java.util.Map.of("message", "Seller rejected successfully");
    }

    @GetMapping("/sellers")
    public List<User> getVerifiedSellers(
            @RequestHeader("X-ADMIN-ID") Long adminId) {

        adminAuthUtil.validateAdmin(adminId);
        return adminUserService.getVerifiedSellers();
    }

    @DeleteMapping("/sellers/{sellerId}")
    public java.util.Map<String, String> deleteSeller(
            @PathVariable Long sellerId,
            @RequestHeader("X-ADMIN-ID") Long adminId) {

        adminAuthUtil.validateAdmin(adminId);
        adminUserService.deleteSeller(sellerId);
        return java.util.Map.of("message", "Seller removed successfully");
    }

    @GetMapping("/premium")
    public List<User> getPremiumUsers(
            @RequestHeader("X-ADMIN-ID") Long adminId) {

        adminAuthUtil.validateAdmin(adminId);
        return adminUserService.getPremiumUsers();
    }

    @PostMapping("/{userId}/premium")
    public java.util.Map<String, String> grantPremium(
            @PathVariable Long userId,
            @RequestHeader("X-ADMIN-ID") Long adminId) {

        adminAuthUtil.validateAdmin(adminId);
        adminUserService.grantPremium(userId);
        return java.util.Map.of("message", "Premium granted successfully");
    }

    @DeleteMapping("/{userId}/premium")
    public java.util.Map<String, String> revokePremium(
            @PathVariable Long userId,
            @RequestHeader("X-ADMIN-ID") Long adminId) {

        adminAuthUtil.validateAdmin(adminId);
        adminUserService.revokePremium(userId);
        return java.util.Map.of("message", "Premium revoked successfully");
    }
}
