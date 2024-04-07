package com.server.coupon.infrastructure.coupon;

import com.server.coupon.domain.coupon.Coupon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.server.coupon.fixture.coupon.CouponFixture.쿠픈_생성_독자_사용_할인율_10_퍼센트;
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
        assertThat(savedCoupon).usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(coupon);
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

    @Test
    void id에_포함되는_쿠폰을_모두_조회한다() {
        // given
        Coupon savedCoupon = couponRepository.save(쿠픈_생성_독자_사용_할인율_10_퍼센트());

        // when
        List<Coupon> result = couponRepository.findAllByIdIn(List.of(savedCoupon.getId()));

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).hasSize(1);
            softly.assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(savedCoupon);
        });
    }

    @Test
    void id에_포함되는_쿠폰의_개수를_반환한다() {
        // given
        Coupon savedCoupon = couponRepository.save(쿠픈_생성_독자_사용_할인율_10_퍼센트());

        // when
        int result = couponRepository.countAllByIdIn(List.of(savedCoupon.getId(), -1L));

        // then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void 쿠폰이_존재하면_true를_반환한다() {
        // given
        Coupon savedCoupon = couponRepository.save(쿠픈_생성_독자_사용_할인율_10_퍼센트());

        // when
        boolean result = couponRepository.existsById(savedCoupon.getId());

        // then
        assertThat(result).isTrue();
     }
}
