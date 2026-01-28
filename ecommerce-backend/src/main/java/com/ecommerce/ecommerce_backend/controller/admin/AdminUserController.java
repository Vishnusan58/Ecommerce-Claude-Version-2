package com.ecommerce.ecommerce_backend.controller.admin;

import com.ecommerce.ecommerce_backend.dto.admin.AdminUserResponseDTO;
import com.ecommerce.ecommerce_backend.enums.UserRole;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.admin.AdminUserService;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.util.AdminAuthUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<AdminUserResponseDTO> getAllUsers(
            @RequestHeader("X-ADMIN-ID") Long adminId) {

        User admin = authService.getUserById(adminId);

        return adminUserService.getAllUsers(admin)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{userId}/role")
    public void changeUserRole(
            @RequestHeader("X-ADMIN-ID") Long adminId,
            @PathVariable Long userId,
            @RequestParam UserRole role) {

        User admin = authService.getUserById(adminId);
        adminUserService.changeUserRole(admin, userId, role);
    }

    private AdminUserResponseDTO mapToDto(User user) {
        AdminUserResponseDTO dto = new AdminUserResponseDTO();
        dto.setUserId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
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

    @PutMapping("/sellers/{sellerId}/approve")
    public String approveSeller(
            @PathVariable Long sellerId,
            @RequestHeader("X-ADMIN-ID") Long adminId) {

        adminAuthUtil.validateAdmin(adminId);
        adminUserService.approveSeller(sellerId);
        return "Seller approved successfully";
    }


}
