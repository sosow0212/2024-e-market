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

    public static Coupon 쿠픈_생성_함께_사용_할인율_20_퍼센트() {
        return Coupon.builder()
                .id(1L)
                .description(Description.builder()
                        .name("쿠폰명")
                        .content("쿠폰 내용")
                        .build())
                .policy(Policy.builder()
                        .amount(20)
                        .canUseAlone(false)
                        .isDiscountPercentage(true)
                        .build())
                .build();
    }

    public static Coupon 쿠픈_생성_함께_사용_할인금액_10000원() {
        return Coupon.builder()
                .id(1L)
                .description(Description.builder()
                        .name("10000원 쿠폰")
                        .content("다른 쿠폰과 함께 사용 가능")
                        .build())
                .policy(Policy.builder()
                        .amount(10000)
                        .canUseAlone(false)
                        .isDiscountPercentage(false)
                        .build())
                .build();
    }
}
