package com.server.member.application.member.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TradeHistoryCreateRequest(
        @NotNull(message = "구매자의 id를 입력해주세요")
        Long buyerId,

        @NotNull(message = "구매자의 id를 입력해주세요")
        Long sellerId,

        @NotNull(message = "상품의 id를 입력해주세요")
        Long productId,

        @NotNull(message = "할인 전 상품의 가격을 입력해주세요")
        Integer productOriginPrice,

        @NotNull(message = "할인 후 상품의 가격을 입력해주세요")
        Integer productDiscountPrice,

        List<Long> usingCouponIds
) {
}
