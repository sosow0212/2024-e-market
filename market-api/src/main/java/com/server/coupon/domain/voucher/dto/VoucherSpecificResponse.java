package com.server.coupon.domain.voucher.dto;

import java.time.LocalDateTime;

public record VoucherSpecificResponse(
        Long voucherId,
        Long couponId,
        String couponName,
        Boolean canUseAloneCoupon,
        Boolean isDiscountPercentageCoupon,
        Integer discountAmount,
        String voucherDescription,
        String voucherNumber,
        Boolean isPublicVoucher,
        Boolean isUsedVoucher,
        LocalDateTime createDate
) {
}
