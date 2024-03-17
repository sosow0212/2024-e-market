package com.market.coupon.domain.voucher.event;

public record UsedVoucherEvent(
        Long couponId,
        Long memberId
) {
}
