package com.market.coupon.ui.coupon.dto;

import com.market.coupon.domain.coupon.Coupon;

public record CouponResponse(
        Long couponId,
        String name,
        String content,
        boolean canUseAlone,
        boolean isDiscountPercentage,
        int amount
) {

    public static CouponResponse from(final Coupon coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.description.getName(),
                coupon.description.getContent(),
                coupon.policy.isCanUseAlone(),
                coupon.policy.isDiscountPercentage(),
                coupon.policy.getAmount()
        );
    }
}
