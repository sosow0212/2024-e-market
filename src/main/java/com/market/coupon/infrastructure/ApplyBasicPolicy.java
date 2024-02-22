package com.market.coupon.infrastructure;

import com.market.coupon.domain.ApplyPolicy;
import com.market.coupon.domain.Coupon;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplyBasicPolicy implements ApplyPolicy {

    /**
     * BasicPolicy : 값을 '쿠폰 금액만큼' 할인 후, 퍼센트 할인을 진행한다
     */

    @Override
    public int apply(final Integer price,
                     final List<Coupon> percentageCoupons,
                     final List<Coupon> discountCoupons) {

        int afterPrice = price;

        for (Coupon discountCoupon : discountCoupons) {
            afterPrice = discountCoupon.discount(afterPrice);
        }

        for (Coupon percentageCoupon : percentageCoupons) {
            afterPrice = percentageCoupon.discount(afterPrice);
        }

        return afterPrice;
    }
}
