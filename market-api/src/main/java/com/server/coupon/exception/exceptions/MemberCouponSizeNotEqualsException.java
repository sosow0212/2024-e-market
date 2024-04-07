package com.server.coupon.exception.exceptions;

public class MemberCouponSizeNotEqualsException extends RuntimeException {

    public MemberCouponSizeNotEqualsException() {
        super("사용하고자 제출한 쿠폰 중 존재하지 않는 쿠폰이 있습니다.");
    }
}
