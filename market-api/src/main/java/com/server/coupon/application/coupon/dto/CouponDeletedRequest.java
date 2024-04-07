package com.server.coupon.application.coupon.dto;

import java.util.List;

public record CouponDeletedRequest(
        List<Long> deletedCouponIds
) {
}
