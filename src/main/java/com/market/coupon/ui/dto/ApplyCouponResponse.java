package com.market.coupon.ui.dto;

import java.util.List;

public record ApplyCouponResponse(
        int originPrice,
        int discountPrice,
        List<Long> usingCouponIds
) {
}
