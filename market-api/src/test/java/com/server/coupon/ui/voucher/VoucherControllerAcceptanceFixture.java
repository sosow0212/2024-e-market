package com.server.coupon.ui.voucher;

import com.server.coupon.application.voucher.dto.VoucherCreateRequest;
import com.server.coupon.application.voucher.dto.VoucherNumberRequest;
import com.server.coupon.domain.coupon.Coupon;
import com.server.coupon.domain.coupon.CouponRepository;
import com.server.coupon.domain.coupon.MemberCoupon;
import com.server.coupon.domain.coupon.MemberCouponRepository;
import com.server.coupon.domain.voucher.Voucher;
import com.server.coupon.domain.voucher.VoucherRepository;
import com.server.coupon.domain.voucher.dto.VoucherPageResponse;
import com.server.coupon.domain.voucher.dto.VoucherSpecificResponse;
import com.server.helper.IntegrationHelper;
import com.server.member.domain.auth.TokenProvider;
import com.server.member.domain.member.Member;
import com.server.member.domain.member.MemberRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.server.coupon.fixture.coupon.CouponFixture.쿠픈_생성_독자_사용_할인율_10_퍼센트;
import static com.server.coupon.fixture.voucher.VoucherFixture.바우처_1인용_생성;
import static com.server.member.fixture.member.MemberFixture.일반_유저_생성;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class VoucherControllerAcceptanceFixture extends IntegrationHelper {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    protected Member 멤버_생성() {
        return memberRepository.save(일반_유저_생성());
    }

    protected Coupon 쿠폰_생성() {
        return couponRepository.save(쿠픈_생성_독자_사용_할인율_10_퍼센트());
    }

    protected Voucher 바우처_생성() {
        return voucherRepository.save(바우처_1인용_생성());
    }

    protected String 토큰_생성() {
        return tokenProvider.create(1L);
    }

    protected VoucherCreateRequest 바우처_생성_요청서() {
        return new VoucherCreateRequest(1L, "description");
    }

    protected ExtractableResponse<?> 바우처_생성_요청(final String url, final VoucherCreateRequest request, String token) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    protected void 바우처_생성_검증(final ExtractableResponse<?> result) {
        assertSoftly(softly -> {
            softly.assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            softly.assertThat(result.header("LOCATION")).isEqualTo("/api/vouchers/1");
        });
    }

    protected VoucherNumberRequest 바우처_사용_요청서_생성(final Voucher voucher) {
        return new VoucherNumberRequest(voucher.getVoucherNumber());
    }

    protected ExtractableResponse<?> 바우처_사용_요청(final String url, final VoucherNumberRequest request, final String token) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    protected void 바우처_사용_검증(final ExtractableResponse<?> result) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(1L);

        assertSoftly(softly -> {
            softly.assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(memberCoupons).hasSize(1);
            softly.assertThat(memberCoupons.get(0).getCouponId()).isEqualTo(1L);
        });

    }

    protected ExtractableResponse<?> 바우처_페이징_조회_요청(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 바우처_페이징_조회_검증(final ExtractableResponse<?> result) {
        VoucherPageResponse expect = result.as(VoucherPageResponse.class);

        assertSoftly(softly -> {
            softly.assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(expect.nextPage()).isEqualTo(-1);
            softly.assertThat(expect.vouchers()).hasSize(1);
        });
    }

    protected ExtractableResponse<?> 바우처_상세_조회_요청(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 바우처_상세_조회_검증(final ExtractableResponse<?> result) {
        VoucherSpecificResponse expect = result.as(VoucherSpecificResponse.class);

        assertSoftly(softly -> {
            softly.assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(expect.voucherId()).isEqualTo(1L);
            softly.assertThat(expect.couponId()).isEqualTo(1L);
        });
    }
}
