package com.market.market.application.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UsingCouponRequest(
        List<Long> usingCouponIds,

        @NotNull(message = "상품의 정상가를 입력해주세요")
        Integer productOriginalPrice,

        @NotNull(message = "쿠폰 적용 후 상품의 할인가를 입력해주세요")
        Integer productDiscountPrice
) {
}
