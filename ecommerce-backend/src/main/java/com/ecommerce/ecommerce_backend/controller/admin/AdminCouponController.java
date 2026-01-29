package com.ecommerce.ecommerce_backend.controller.admin;

import com.ecommerce.ecommerce_backend.dto.admin.CreateCouponDTO;
import com.ecommerce.ecommerce_backend.model.Coupon;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.service.admin.AdminCouponService;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.util.AdminAuthUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/coupons")
public class AdminCouponController {

    private final AdminCouponService couponService;
    private final AuthService authService;
    private final AdminAuthUtil adminAuthUtil;

    public AdminCouponController(AdminCouponService couponService,
                                 AuthService authService,
                                 AdminAuthUtil adminAuthUtil) {
        this.couponService = couponService;
        this.authService = authService;
        this.adminAuthUtil = adminAuthUtil;
    }

    @PostMapping
    public Coupon createCoupon(
            @RequestHeader("X-ADMIN-ID") Long adminId,
            @RequestBody CreateCouponDTO dto) {

        adminAuthUtil.validateAdmin(adminId);

        Coupon coupon = new Coupon();
        coupon.setCode(dto.getCode().toUpperCase());
        coupon.setDiscountType(dto.getDiscountType());
        coupon.setDiscountValue(dto.getDiscountValue());
        coupon.setMinOrderValue(dto.getMinOrderValue());
        coupon.setMaxDiscount(dto.getMaxDiscount());
        coupon.setValidFrom(dto.getValidFrom());
        coupon.setValidUntil(dto.getValidUntil());
        coupon.setUsageLimit(dto.getUsageLimit());
        coupon.setUsedCount(0);
        coupon.setActive(true);

        User admin = authService.getUserById(adminId);
        return couponService.createCoupon(admin, coupon);
    }

    @GetMapping
    public List<Coupon> getAllCoupons(
            @RequestHeader("X-ADMIN-ID") Long adminId) {

        User admin = authService.getUserById(adminId);
        return couponService.getAllCoupons(admin);
    }

    @DeleteMapping("/{couponId}")
    public Map<String, String> deleteCoupon(
            @RequestHeader("X-ADMIN-ID") Long adminId,
            @PathVariable Long couponId) {

        adminAuthUtil.validateAdmin(adminId);
        couponService.deleteCoupon(couponId);
        return Map.of("message", "Coupon deleted successfully");
    }

    @PutMapping("/{couponId}/toggle")
    public Coupon toggleCouponStatus(
            @RequestHeader("X-ADMIN-ID") Long adminId,
            @PathVariable Long couponId) {

        adminAuthUtil.validateAdmin(adminId);
        return couponService.toggleCouponStatus(couponId);
    }
}
