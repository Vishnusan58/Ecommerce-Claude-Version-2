package com.ecommerce.ecommerce_backend.util;


import com.ecommerce.ecommerce_backend.model.Coupon;

public class CouponCalculatorUtil {

    private CouponCalculatorUtil() {
        // utility class
    }

    public static double calculateDiscount(
            Coupon coupon,
            double orderAmount
    ) {
        if (coupon == null || !coupon.isActive()) {
            return 0;
        }

        if (orderAmount < coupon.getMinOrderAmount()) {
            return 0;
        }

        double discount;

        if ("PERCENT".equalsIgnoreCase(coupon.getDiscountType())) {
            discount = (orderAmount * coupon.getDiscountValue()) / 100;
        } else {
            discount = coupon.getDiscountValue();
        }

        return Math.min(discount, coupon.getMaxDiscountAmount());
    }
}
