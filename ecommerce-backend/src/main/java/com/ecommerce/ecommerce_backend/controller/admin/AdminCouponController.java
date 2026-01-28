package com.ecommerce.ecommerce_backend.controller.admin;

import com.ecommerce.ecommerce_backend.dto.admin.AdminCouponDTO;
import com.ecommerce.ecommerce_backend.model.Coupon;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.admin.AdminCouponService;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/coupons")
public class AdminCouponController {

    private final AdminCouponService couponService;
    private final AuthService authService;

    public AdminCouponController(AdminCouponService couponService,
                                 AuthService authService) {
        this.couponService = couponService;
        this.authService = authService;
    }

    @PostMapping
    public Coupon createCoupon(
            @RequestHeader("X-ADMIN-ID") Long adminId,
            @RequestBody Coupon coupon) {

        User admin = authService.getUserById(adminId);
        return couponService.createCoupon(admin, coupon);
    }

    @GetMapping
    public List<AdminCouponDTO> getAllCoupons(
            @RequestHeader("X-ADMIN-ID") Long adminId) {

        User admin = authService.getUserById(adminId);

        return couponService.getAllCoupons(admin)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private AdminCouponDTO mapToDto(Coupon coupon) {
        AdminCouponDTO dto = new AdminCouponDTO();
        dto.setCouponId(coupon.getId());
        dto.setCode(coupon.getCode());
        dto.setDiscountType(coupon.getDiscountType());
        dto.setDiscountValue(coupon.getDiscountValue());
        dto.setActive(coupon.isActive());
        return dto;
    }
}
