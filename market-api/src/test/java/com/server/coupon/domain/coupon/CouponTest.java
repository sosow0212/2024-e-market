package com.server.coupon.domain.coupon;

import com.server.coupon.exception.exceptions.CouponAmountRangeInvalidException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.server.coupon.fixture.coupon.CouponFixture.쿠픈_생성_독자_사용_할인율_10_퍼센트;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CouponTest {

    @Test
    void 할인율_쿠폰인_경우_생성시_할인_범위가_올바르지_않다면_예외를_발생시킨다() {
        // when & then
        assertThatThrownBy(() -> Coupon.createCoupon("coupon", "content", true, true, 300))
                .isInstanceOf(CouponAmountRangeInvalidException.class);
    }

    @Test
    void 독자_사용_쿠폰이면_true를_반환한다() {
        // given
        Coupon coupon = 쿠픈_생성_독자_사용_할인율_10_퍼센트();

        // when
        boolean result = coupon.isUsingAloneCoupon();

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 할인율_쿠폰이면_true를_반환한다() {
        // given
        Coupon coupon = 쿠픈_생성_독자_사용_할인율_10_퍼센트();

        // when
        boolean result = coupon.isPercentageCoupon();

        // then
        assertThat(result).isTrue();
    }
}
