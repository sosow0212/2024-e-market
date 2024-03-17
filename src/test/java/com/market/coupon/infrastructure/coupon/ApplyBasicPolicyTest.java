package com.market.coupon.infrastructure.coupon;

import com.market.coupon.domain.coupon.Coupon;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.market.coupon.fixture.coupon.CouponFixture.쿠픈_생성_함께_사용_할인금액_10000원;
import static com.market.coupon.fixture.coupon.CouponFixture.쿠픈_생성_함께_사용_할인율_20_퍼센트;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ApplyBasicPolicyTest {

    private final ApplyBasicPolicy applyBasicPolicy = new ApplyBasicPolicy();

    @Test
    void 값을_쿠폰_금액만큼_할인_후_퍼센트_할인을_진행한다() {
        // given
        List<Coupon> percentageCoupons = List.of(쿠픈_생성_함께_사용_할인율_20_퍼센트());
        List<Coupon> discountCoupons = List.of(쿠픈_생성_함께_사용_할인금액_10000원());
        int price = 100000;

        // when
        int afterPrice = applyBasicPolicy.apply(price, percentageCoupons, discountCoupons);

        // then
        assertThat(afterPrice).isEqualTo(72000);
    }
}
