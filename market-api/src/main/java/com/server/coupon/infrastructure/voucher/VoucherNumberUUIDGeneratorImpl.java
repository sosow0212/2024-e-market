package com.server.coupon.infrastructure.voucher;

import com.server.coupon.domain.voucher.VoucherNumberGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherNumberUUIDGeneratorImpl implements VoucherNumberGenerator {

    private static final String VOUCHER_NUMBER_FORMAT = "%s-%s-%s-%s";

    @Override
    public String generate() {
        String voucherNumber = makeVoucherNumber();

        return String.format(VOUCHER_NUMBER_FORMAT,
                makeFirstFields(voucherNumber),
                makeSecondFields(voucherNumber),
                makeThirdFields(voucherNumber),
                makeFourthFields(voucherNumber)
        );
    }

    private static String makeVoucherNumber() {
        UUID uuid = UUID.randomUUID();

        return uuid.toString()
                .toUpperCase()
                .replace("-", "");
    }

    private static String makeFirstFields(final String couponCode) {
        return couponCode.substring(0, 6);
    }

    private static String makeSecondFields(final String couponCode) {
        return couponCode.substring(6, 12);
    }

    private static String makeThirdFields(final String couponCode) {
        return couponCode.substring(12, 18);
    }

    private static String makeFourthFields(final String couponCode) {
        return couponCode.substring(18, 24);
    }
}
