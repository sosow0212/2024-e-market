package com.market.coupon.domain;

import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class Coupons {

    private final List<Coupon> coupons;

    public void applyCoupons() {

    }

    public List<Coupon> getCoupons() {
        return Collections.unmodifiableList(coupons);
    }
}
