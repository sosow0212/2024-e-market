package com.server.coupon.exception.exceptions;

public class UsingAloneCouponContainsException extends RuntimeException {

    public UsingAloneCouponContainsException() {
        super("단독 사용 쿠폰이 포함되어 있습니다. 나머지 쿠폰을 제외하고 사용해주세요.");
    }
}
