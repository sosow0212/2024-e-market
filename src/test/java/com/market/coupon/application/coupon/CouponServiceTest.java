package com.market.coupon.application.coupon;

import com.market.coupon.application.coupon.dto.CouponCreateRequest;
import com.market.coupon.application.coupon.dto.MemberCouponCreateRequest;
import com.market.coupon.domain.coupon.Coupon;
import com.market.coupon.domain.coupon.CouponRepository;
import com.market.coupon.domain.coupon.MemberCoupon;
import com.market.coupon.domain.coupon.MemberCouponRepository;
import com.market.coupon.exception.exceptions.ContainsNotExistedCouponException;
import com.market.coupon.exception.exceptions.CouponAmountRangeInvalidException;
import com.market.coupon.exception.exceptions.CouponNotFoundException;
import com.market.coupon.exception.exceptions.MemberCouponSizeNotEqualsException;
import com.market.coupon.exception.exceptions.UsingAloneCouponContainsException;
import com.market.coupon.infrastructure.coupon.ApplyBasicPolicy;
import com.market.coupon.infrastructure.coupon.CouponFakeRepository;
import com.market.coupon.infrastructure.coupon.MemberCouponFakeRepository;
import com.market.global.exception.exception.AuthenticationInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.market.coupon.fixture.coupon.CouponFixture.쿠픈_생성_독자_사용_할인율_10_퍼센트;
import static com.market.coupon.fixture.coupon.CouponFixture.쿠픈_생성_함께_사용_할인금액_10000원;
import static com.market.coupon.fixture.coupon.MemberCouponFixture.멤버_쿠폰_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
        couponService = new CouponService(couponRepository, memberCouponRepository, new ApplyBasicPolicy());
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
            assertDoesNotThrow(() -> couponService.saveMemberCoupons(coupon.getId(), new MemberCouponCreateRequest(List.of(1L))));
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
            couponRepository.save(쿠픈_생성_독자_사용_할인율_10_퍼센트());
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

        // when
        couponService.deleteUsedMemberCoupons(memberCoupon.getMemberId(), List.of(coupon.getId()));

        // then
        List<MemberCoupon> result = memberCouponRepository.findAllByMemberId(memberCoupon.getMemberId());
        assertThat(result).isEmpty();
    }

    @DisplayName("멤버가 사용한 쿠폰의 존재 유효성 검사")
    @Nested
    class ValidateMemberCouponSize {

        @Test
        void 멤버가_사용한_쿠폰이_존재하는지_확인한다() {
            // given
            MemberCoupon memberCoupon = 멤버_쿠폰_생성();
            MemberCoupon memberCoupon2 = MemberCoupon.builder()
                    .couponId(2L)
                    .memberId(1L)
                    .build();

            memberCouponRepository.save(memberCoupon);
            memberCouponRepository.save(memberCoupon2);

            // when & then
            assertDoesNotThrow(() -> couponService.validateMemberCouponsExisted(1L, List.of(memberCoupon.getCouponId(), memberCoupon2.getCouponId())));
        }

        @Test
        void 쿠폰을_적용하지_않으면_예외를_발생하지_않는다() {
            // when & then
            assertDoesNotThrow(() -> couponService.validateMemberCouponsExisted(1L, List.of()));
        }

        @Test
        void 멤버가_사용한_쿠폰이_존재하지_않으면_예외를_발생한다() {
            // given
            MemberCoupon memberCoupon = 멤버_쿠폰_생성();
            memberCouponRepository.save(memberCoupon);

            // when & then
            assertThatThrownBy(() -> couponService.validateMemberCouponsExisted(1L, List.of(memberCoupon.getCouponId(), 2L)))
                    .isInstanceOf(MemberCouponSizeNotEqualsException.class);
        }
    }

    @DisplayName("유저가 쿠폰을 사용하여 가격을 할인한다")
    @Nested
    class ApplyCoupons {

        @Test
        void 쿠폰을_정상적으로_적용한다() {
            // given
            Coupon savedCoupon = couponRepository.save(쿠픈_생성_함께_사용_할인금액_10000원());

            // when
            int result = couponService.applyCoupons(10000, List.of(savedCoupon.getId()));

            // then
            assertThat(result).isZero();
        }

        @Test
        void 독립_사용_쿠폰이_다른_쿠폰과_함께_적용되면_예외를_발생시킨다() {
            // given
            Coupon savedCoupon = couponRepository.save(쿠픈_생성_함께_사용_할인금액_10000원());
            Coupon savedCoupon2 = couponRepository.save(쿠픈_생성_독자_사용_할인율_10_퍼센트());

            // when & then
            assertThatThrownBy(() -> couponService.applyCoupons(10000, List.of(savedCoupon.getId(), savedCoupon2.getId())))
                    .isInstanceOf(UsingAloneCouponContainsException.class);
        }

        @Test
        void 적용하는_쿠폰_중_존재하지_않는_쿠폰이_있다면_예외를_발생시킨다() {
            // given
            Coupon savedCoupon = couponRepository.save(쿠픈_생성_함께_사용_할인금액_10000원());

            // when & then
            assertThatThrownBy(() -> couponService.applyCoupons(10000, List.of(savedCoupon.getId(), -1L)))
                    .isInstanceOf(ContainsNotExistedCouponException.class);
         }
    }
}
