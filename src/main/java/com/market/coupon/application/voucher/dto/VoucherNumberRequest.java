package com.market.coupon.application.voucher.dto;

import jakarta.validation.constraints.NotBlank;

public record VoucherNumberRequest(
        @NotBlank(message = "바우처 번호를 입력해주세요.")
        String voucherNumber
) {
}
