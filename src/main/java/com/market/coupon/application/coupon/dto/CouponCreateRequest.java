package com.market.coupon.application.coupon.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CouponCreateRequest(

        @NotEmpty(message = "쿠폰 이름을 입력해주세요")
        String name,

        @NotEmpty(message = "쿠폰 설명을 입력해주세요")
        String content,

        @NotNull(message = "독립 사용 쿠폰인지 입력해주세요")
        Boolean canUseAlone,

        @NotNull(message = "확률성 쿠폰인지 입력해주세요")
        Boolean isDiscountPercentage,

        @NotNull(message = "쿠폰 할인 값을 입력해주세요")
        Integer amount
) {
}
