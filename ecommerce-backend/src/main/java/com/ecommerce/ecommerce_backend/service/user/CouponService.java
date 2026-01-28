package com.ecommerce.ecommerce_backend.service.user;

import com.ecommerce.ecommerce_backend.dto.coupon.CouponResponseDTO;
import com.ecommerce.ecommerce_backend.model.Coupon;
import com.ecommerce.ecommerce_backend.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    /**
     * Apply coupon by code
     */
    public CouponResponseDTO applyCoupon(String couponCode) {

        CouponResponseDTO response = new CouponResponseDTO();

        Coupon coupon = couponRepository.findByCode(couponCode)
                .orElse(null);

        if (coupon == null || !coupon.isActive()) {
            response.setApplied(false);
            response.setCode(couponCode);
            response.setDiscountAmount(0);
            return response;
        }

        // For now: return discount value directly
        response.setApplied(true);
        response.setCode(coupon.getCode());
        response.setDiscountAmount(coupon.getDiscountValue());

        return response;
    }
}
