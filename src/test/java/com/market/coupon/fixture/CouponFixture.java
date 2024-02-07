package com.market.coupon.fixture;

import com.market.coupon.domain.Coupon;
import com.market.coupon.domain.Description;
import com.market.coupon.domain.Policy;

public class CouponFixture {

    public static Coupon 쿠픈_생성_독자_사용_할인율_10_퍼센트() {
        return Coupon.builder()
                .id(1L)
                .description(Description.builder()
                        .name("쿠폰명")
                        .content("쿠폰 내용")
                        .build())
                .policy(Policy.builder()
                        .amount(10)
                        .canUseAlone(true)
                        .isDiscountPercentage(true)
                        .build())
                .build();
    }
}
