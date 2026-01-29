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

        if (orderAmount < coupon.getMinOrderValue()) {
            return 0;
        }

        double discount;

        if ("PERCENTAGE".equalsIgnoreCase(coupon.getDiscountType())) {
            discount = (orderAmount * coupon.getDiscountValue()) / 100;
        } else {
            discount = coupon.getDiscountValue();
        }

        Double maxDiscount = coupon.getMaxDiscount();
        if (maxDiscount != null && maxDiscount > 0) {
            return Math.min(discount, maxDiscount);
        }
        return discount;
    }
}
