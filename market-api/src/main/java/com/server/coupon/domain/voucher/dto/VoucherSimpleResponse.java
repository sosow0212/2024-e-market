package com.server.coupon.domain.voucher.dto;

import java.time.LocalDateTime;

public record VoucherSimpleResponse(
        Long id,
        Long couponId,
        String description,
        Boolean isPublic,
        Boolean isUsed,
        LocalDateTime createDate
) {
}
