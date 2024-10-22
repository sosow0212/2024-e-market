package com.server.coupon.infrastructure.coupon;

import com.server.coupon.domain.coupon.MemberCoupon;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.server.coupon.fixture.coupon.MemberCouponFixture.멤버_쿠폰_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class MemberCouponJpaRepositoryTest {

    @Autowired
    private MemberCouponJpaRepository memberCouponRepository;

    @Test
    void 멤버가_소유한_모든_쿠폰을_조회한다() {
        // given
        MemberCoupon memberCoupon = 멤버_쿠폰_생성();
        MemberCoupon saved = memberCouponRepository.save(memberCoupon);

        // when
        List<MemberCoupon> result = memberCouponRepository.findAllByMemberId(memberCoupon.getMemberId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).hasSize(1);
            softly.assertThat(result.get(0))
                    .usingRecursiveComparison()
                    .isEqualTo(saved);
        });
    }

    @Test
    void 멤버에게_쿠폰을_추가시킨다() {
        // given
        MemberCoupon memberCoupon = 멤버_쿠폰_생성();

        // when
        MemberCoupon saved = memberCouponRepository.save(memberCoupon);

        // then
        assertThat(saved)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(memberCoupon);
    }

    @Test
    void 멤버가_사용한_쿠폰을_모두_제거한다() {
        // given
        MemberCoupon memberCoupon = 멤버_쿠폰_생성();

        // when
        memberCouponRepository.deleteByMemberIdAndCouponIdIn(memberCoupon.getMemberId(), List.of(memberCoupon.getCouponId()));

        // then
        List<MemberCoupon> response = memberCouponRepository.findAllByMemberId(memberCoupon.getMemberId());
        assertThat(response).isEmpty();
    }

    @Test
    void 멤버가_가진_쿠폰의_수를_반환한다() {
        // given
        MemberCoupon memberCoupon = 멤버_쿠폰_생성();
        MemberCoupon memberCoupon2 = MemberCoupon.builder()
                .couponId(2L)
                .memberId(1L)
                .build();

        memberCouponRepository.save(memberCoupon);
        memberCouponRepository.save(memberCoupon2);

        // when
        int result = memberCouponRepository.countMemberIdWithCouponIds(1L, List.of(1L, 2L));

        // then
        assertThat(result).isEqualTo(2);
     }
}
