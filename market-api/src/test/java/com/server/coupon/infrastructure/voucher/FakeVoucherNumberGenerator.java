package com.server.coupon.infrastructure.voucher;

import com.server.coupon.domain.voucher.VoucherNumberGenerator;

public class FakeVoucherNumberGenerator implements VoucherNumberGenerator {

    @Override
    public String generate() {
        return "number";
    }
}
