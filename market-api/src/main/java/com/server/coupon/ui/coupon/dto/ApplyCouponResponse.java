package com.server.coupon.ui.coupon.dto;

import java.util.List;

public record ApplyCouponResponse(
        int originPrice,
        int discountPrice,
        List<Long> usingCouponIds
) {
}
