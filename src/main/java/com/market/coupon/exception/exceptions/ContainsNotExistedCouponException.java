package com.market.coupon.exception.exceptions;

public class ContainsNotExistedCouponException extends RuntimeException {

    public ContainsNotExistedCouponException() {
        super("존재하지 않는 쿠폰이 포함되어 있습니다.");
    }
}
