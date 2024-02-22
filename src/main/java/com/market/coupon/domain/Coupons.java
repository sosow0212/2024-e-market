package com.market.coupon.domain;

import com.market.coupon.exception.exceptions.UsingAloneCouponContainsException;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class Coupons {

    private static final int MULTIPLE_COUPON_SIZE = 2;

    private final List<Coupon> coupons;

    public List<Coupon> getCoupons() {
        return Collections.unmodifiableList(coupons);
    }

    public int applyCoupons(final Integer price, ApplyPolicy applyPolicy) {
        validateUsingAloneCouponContainsWithOther();

        List<Coupon> percentageCoupons = findPercentageCoupons();
        List<Coupon> discountCoupon = findDiscountCoupons();

        return applyPolicy.apply(price, percentageCoupons, discountCoupon);
    }

    private void validateUsingAloneCouponContainsWithOther() {
        boolean hasUsingAloneCoupon = this.coupons.stream()
                .anyMatch(Coupon::isUsingAloneCoupon);

        if (hasUsingAloneCoupon && this.coupons.size() >= MULTIPLE_COUPON_SIZE) {
            throw new UsingAloneCouponContainsException();
        }
    }

    private List<Coupon> findPercentageCoupons() {
        return coupons.stream()
                .filter(Coupon::isPercentageCoupon)
                .toList();
    }

    private List<Coupon> findDiscountCoupons() {
        return coupons.stream()
                .filter(coupon -> !coupon.isPercentageCoupon())
                .toList();
    }
}
