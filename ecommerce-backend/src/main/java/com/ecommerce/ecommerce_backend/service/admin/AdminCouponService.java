package com.ecommerce.ecommerce_backend.service.admin;

import com.ecommerce.ecommerce_backend.model.Coupon;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.repository.CouponRepository;
import com.ecommerce.ecommerce_backend.util.AdminAuthUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCouponService {
    private final AdminAuthUtil adminAuthUtil;
    private final CouponRepository couponRepository;

    public AdminCouponService(AdminAuthUtil adminAuthUtil, CouponRepository couponRepository) {
        this.adminAuthUtil = adminAuthUtil;
        this.couponRepository = couponRepository;
    }

    public Coupon createCoupon(User admin, Coupon coupon) {
        adminAuthUtil.validateAdmin(admin);
        return couponRepository.save(coupon);
    }

    public List<Coupon> getAllCoupons(User admin) {
        adminAuthUtil.validateAdmin(admin);
        return couponRepository.findAll();
    }

    public void deactivateCoupon(User admin, Long couponId) {
        adminAuthUtil.validateAdmin(admin);

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        coupon.setActive(false);
        couponRepository.save(coupon);
    }
}
