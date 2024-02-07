package com.market.coupon.application.dto;

import java.util.List;

public record MemberCouponCreateRequest(
        List<Long> couponIds
) {
}
