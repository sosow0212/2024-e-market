package com.market.coupon.application.dto;

import java.util.List;

public record CouponDeletedRequest(
        List<Long> deletedCouponIds
) {
}
