package com.market.coupon.domain.coupon;

import java.util.List;

public interface ApplyPolicy {

    int apply(Integer price,
              List<Coupon> percentageCoupons,
              List<Coupon> discountCoupons
    );
}
