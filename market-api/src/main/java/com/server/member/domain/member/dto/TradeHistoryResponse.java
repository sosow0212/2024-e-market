package com.server.member.domain.member.dto;

public record TradeHistoryResponse(
        Long tradeHistoryId,
        String buyerName,
        String sellerName,
        String productTitle,
        int productOriginPrice,
        int productDiscountPrice,
        String usingCouponIds
) {
}
