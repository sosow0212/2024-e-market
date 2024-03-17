package com.market.coupon.fixture.voucher;

import com.market.coupon.domain.voucher.Voucher;

public class VoucherFixture {

    public static Voucher 바우처_1인용_생성() {
        return Voucher.builder()
                .id(1L)
                .voucherNumber("private")
                .description("한 명이 사용할 수 있는 바우처")
                .isUsed(false)
                .isPublic(false)
                .couponId(1L)
                .build();
    }

    public static Voucher 바우처_프로모션용_재사용_생성() {
        return Voucher.builder()
                .id(1L)
                .voucherNumber("public")
                .description("여러 명이 사용할 수 있는 바우처")
                .isUsed(false)
                .isPublic(true)
                .couponId(1L)
                .build();
    }
}
