package com.server.coupon.domain.voucher.event;

public record UsedVoucherEvent(
        Long couponId,
        Long memberId
) {
}
