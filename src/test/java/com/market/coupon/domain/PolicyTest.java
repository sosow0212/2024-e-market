package com.market.coupon.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class PolicyTest {

    @Nested
    class DiscountPrice {

        @Test
        void 할인율_계산() {
            // given
            int amount = 10;
            int price = 10000;
            Policy policy = new Policy(false, true, amount);

            // when
            int afterPrice = policy.discount(price);

            // then
            assertThat(afterPrice).isEqualTo((int) (price * 0.9));
        }

        @Test
        void 값_할인_계산() {
            // given
            int amount = 1000;
            int price = 10000;
            Policy policy = new Policy(false, false, amount);

            // when
            int afterPrice = policy.discount(price);

            // then
            assertThat(afterPrice).isEqualTo(price - amount);
        }

        @Test
        void 값_할인_계산_할인금액이_더_큰_경우_0원_반환을_한다() {
            // given
            int amount = 1000000;
            int price = 10000;
            Policy policy = new Policy(false, false, amount);

            // when
            int afterPrice = policy.discount(price);

            // then
            assertThat(afterPrice).isEqualTo(0);
        }
    }
}
