package com.market.member.application.member.dto;

import java.util.List;

public record TradeHistoryCreateRequest(
        Long buyerId,
        Long sellerId,
        Long productId,
        Integer productOriginPrice,
        Integer productDiscountPrice,
        List<Long> usingCouponIds
) {
}
