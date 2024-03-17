package com.market.coupon.exception.exceptions;

public class VoucherNumbersNotEqualsException extends RuntimeException {

    public VoucherNumbersNotEqualsException() {
        super("바우처 번호가 일치하지 않습니다.");
    }
}
