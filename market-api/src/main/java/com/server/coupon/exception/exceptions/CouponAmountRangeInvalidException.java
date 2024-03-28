package com.server.coupon.exception.exceptions;

public class CouponAmountRangeInvalidException extends RuntimeException {

    public CouponAmountRangeInvalidException() {
        super("할인 정책이 %인 경우 쿠폰의 할인율은 0~100까지 입력이 가능합니다");
    }
}
