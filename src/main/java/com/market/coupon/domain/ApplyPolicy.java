package com.market.coupon.domain;

import java.util.List;

public interface ApplyPolicy {

    int apply(Integer price,
              List<Coupon> percentageCoupons,
              List<Coupon> discountCoupons
    );
}
