package com.market.coupon.application.voucher.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VoucherCreateRequest(
        @NotNull(message = "쿠폰의 id를 입력해주세요.")
        Long couponId,

        @NotBlank(message = "바우처 설명을 입력해주세요.")
        String description
) {
}
