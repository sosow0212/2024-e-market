package com.server.coupon.exception.exceptions;

public class CouponNotFoundException extends RuntimeException {

    public CouponNotFoundException() {
        super("쿠폰을 찾을 수 없습니다");
    }
}
