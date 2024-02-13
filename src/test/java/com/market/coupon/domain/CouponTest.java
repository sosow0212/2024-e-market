package com.market.coupon.domain;

import com.market.coupon.exception.exceptions.CouponAmountRangeInvalidException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

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
}
