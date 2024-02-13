package com.market.coupon.application;

import com.market.coupon.application.dto.CouponCreateRequest;
import com.market.coupon.application.dto.CouponDeletedRequest;
import com.market.coupon.application.dto.MemberCouponCreateRequest;
import com.market.coupon.domain.Coupon;
import com.market.coupon.domain.CouponRepository;
import com.market.coupon.domain.MemberCoupon;
import com.market.coupon.domain.MemberCouponRepository;
import com.market.coupon.exception.exceptions.CouponAmountRangeInvalidException;
import com.market.coupon.exception.exceptions.CouponNotFoundException;
import com.market.coupon.infrastructure.CouponFakeRepository;
import com.market.coupon.infrastructure.MemberCouponFakeRepository;
import com.market.global.exception.exception.AuthenticationInvalidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.market.coupon.fixture.CouponFixture.쿠픈_생성_독자_사용_할인율_10_퍼센트;
import static com.market.coupon.fixture.MemberCouponFixture.멤버_쿠폰_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CouponServiceTest {

    private CouponRepository couponRepository;
    private MemberCouponRepository memberCouponRepository;
    private CouponService couponService;

    @BeforeEach
    void setup() {
        couponRepository = new CouponFakeRepository();
        memberCouponRepository = new MemberCouponFakeRepository();
        couponService = new CouponService(couponRepository, memberCouponRepository);
    }

    @DisplayName("쿠폰을 생성하는 경우")
    @Nested
    class CouponCreate {

        @Test
        void 새로운_쿠폰을_생성한다() {
            // given
            CouponCreateRequest req = new CouponCreateRequest("쿠폰", "설명", false, false, 10);

            // when
            Long result = couponService.saveNewCoupon(req);

            // then
            assertThat(result).isEqualTo(1L);
        }

        @Test
        void 쿠폰_할인_범위가_잘못되면_예외를_반환한다() {
            // given
            CouponCreateRequest request = new CouponCreateRequest("쿠폰", "설명", false, true, 1000);

            // when & then
            assertThatThrownBy(() -> couponService.saveNewCoupon(request))
                    .isInstanceOf(CouponAmountRangeInvalidException.class);
        }
    }

    @DisplayName("멤버에게_쿠폰을_등록시키는_경우")
    @Nested
    class saveMemberCoupon {

        @Test
        void 멤버에게_쿠폰을_등록시킨다() {
            // given
            Coupon coupon = couponRepository.save(쿠픈_생성_독자_사용_할인율_10_퍼센트());

            // when & then
            Assertions.assertDoesNotThrow(() -> couponService.saveMemberCoupons(coupon.getId(), new MemberCouponCreateRequest(List.of(1L))));
        }

        @Test
        void 쿠폰이_존재하지_않는_경우_멤버에게_쿠폰을_등록시키지_못한다() {
            // when & then
            assertThatThrownBy(() -> couponService.saveMemberCoupons(1L, new MemberCouponCreateRequest(List.of(-1L))))
                    .isInstanceOf(CouponNotFoundException.class);
        }
    }

    @DisplayName("멤버가_가진_쿠폰을_조회하는_경우")
    @Nested
    class findMemberCoupons {
        @Test
        void 멤버가_가진_쿠폰을_모두_조회한다() {
            // given
            Coupon coupon = couponRepository.save(쿠픈_생성_독자_사용_할인율_10_퍼센트());
            MemberCoupon memberCoupon = memberCouponRepository.save(멤버_쿠폰_생성());

            // when
            List<Coupon> coupons = couponService.findAllMemberCoupons(memberCoupon.getMemberId(), 1L)
                    .getCoupons();

            // then
            assertSoftly(softly -> {
                softly.assertThat(coupons).hasSize(1);
                softly.assertThat(coupons.get(0))
                        .usingRecursiveComparison()
                        .isEqualTo(coupon);
            });
        }

        @Test
        void 유저_정보가_로그인_한_유저와_동일하지_않으면_예외를_발생시킨다() {
            // given
            Coupon coupon = couponRepository.save(쿠픈_생성_독자_사용_할인율_10_퍼센트());
            MemberCoupon memberCoupon = memberCouponRepository.save(멤버_쿠폰_생성());

            // when & then
            assertThatThrownBy(() -> couponService.findAllMemberCoupons(memberCoupon.getMemberId(), -1L))
                    .isInstanceOf(AuthenticationInvalidException.class);
        }
    }

    @Test
    void 멤버가_사용한_쿠폰을_모두_제거한다() {
        // given
        Coupon coupon = couponRepository.save(쿠픈_생성_독자_사용_할인율_10_퍼센트());
        MemberCoupon memberCoupon = memberCouponRepository.save(멤버_쿠폰_생성());
        CouponDeletedRequest request = new CouponDeletedRequest(List.of(coupon.getId()));

        // when
        couponService.deleteUsingMemberCoupons(memberCoupon.getMemberId(), request);

        // then
        List<MemberCoupon> result = memberCouponRepository.findAllByMemberId(memberCoupon.getMemberId());
        assertThat(result).hasSize(0);
    }
}
