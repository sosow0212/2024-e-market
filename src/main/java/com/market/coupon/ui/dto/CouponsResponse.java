package com.market.coupon.ui.dto;

import com.market.coupon.domain.Coupons;

import java.util.List;

public record CouponsResponse(
        Long memberId,
        List<CouponResponse> coupons
) {

    public static CouponsResponse from(final Long memberId, final Coupons coupons) {
        List<CouponResponse> result = coupons.getCoupons().stream()
                .map(CouponResponse::from)
                .toList();

        return new CouponsResponse(memberId, result);
    }
}
