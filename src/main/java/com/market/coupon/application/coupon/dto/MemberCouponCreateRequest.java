package com.market.coupon.application.coupon.dto;

import java.util.List;

public record MemberCouponCreateRequest(
        List<Long> couponIds
) {
}
