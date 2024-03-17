package com.market.coupon.infrastructure.voucher;

import com.market.coupon.domain.voucher.VoucherNumberGenerator;

public class FakeVoucherNumberGenerator implements VoucherNumberGenerator {

    @Override
    public String generate() {
        return "number";
    }
}
