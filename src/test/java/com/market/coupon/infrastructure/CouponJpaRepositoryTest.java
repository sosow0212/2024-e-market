package com.market.coupon.infrastructure;

import com.market.coupon.domain.Coupon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.market.coupon.fixture.CouponFixture.쿠픈_생성_독자_사용_할인율_10_퍼센트;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class CouponJpaRepositoryTest {

    @Autowired
    private CouponJpaRepository couponRepository;

    @Test
    void 쿠폰을_저장한다() {
        // given
        Coupon coupon = 쿠픈_생성_독자_사용_할인율_10_퍼센트();

        // when
        Coupon savedCoupon = couponRepository.save(coupon);

        // then
        assertThat(savedCoupon.getId()).isEqualTo(1L);
    }

    @Test
    void 쿠폰을_조회한다() {
        // given
        Coupon savedCoupon = couponRepository.save(쿠픈_생성_독자_사용_할인율_10_퍼센트());

        // when
        Optional<Coupon> result = couponRepository.findById(savedCoupon.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).isPresent();
            softly.assertThat(result.get())
                    .usingRecursiveComparison()
                    .isEqualTo(savedCoupon);
        });
    }

    @Test
    void 쿠폰을_제거한다() {
        // given
        Coupon savedCoupon = couponRepository.save(쿠픈_생성_독자_사용_할인율_10_퍼센트());

        // when & then
        Assertions.assertDoesNotThrow(() -> couponRepository.deleteById(savedCoupon.getId()));
    }
}
