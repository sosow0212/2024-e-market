package com.market.coupon.exception.exceptions;

public class VoucherNotFoundException extends RuntimeException {

    public VoucherNotFoundException() {
        super("바우처를 찾을 수 없습니다.");
    }
}
