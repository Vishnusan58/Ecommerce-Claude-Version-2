package com.ecommerce.ecommerce_backend.controller.user;

import com.ecommerce.ecommerce_backend.dto.coupon.ApplyCouponDTO;
import com.ecommerce.ecommerce_backend.dto.coupon.CouponResponseDTO;
import com.ecommerce.ecommerce_backend.service.user.CouponService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping("/apply")
    public CouponResponseDTO applyCoupon(@RequestBody ApplyCouponDTO dto) {
        return couponService.applyCoupon(dto.getCouponCode());
    }
}
