package com.market.coupon.ui;

import com.market.coupon.application.CouponService;
import com.market.coupon.application.dto.CouponCreateRequest;
import com.market.coupon.application.dto.MemberCouponCreateRequest;
import com.market.coupon.domain.CouponRepository;
import com.market.coupon.domain.MemberCouponRepository;
import com.market.coupon.fixture.MemberCouponFixture;
import com.market.coupon.ui.dto.ApplyCouponResponse;
import com.market.coupon.ui.dto.CouponsResponse;
import com.market.helper.IntegrationHelper;
import com.market.member.domain.auth.TokenProvider;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.market.coupon.fixture.CouponFixture.쿠픈_생성_독자_사용_할인율_10_퍼센트;
import static com.market.coupon.fixture.CouponFixture.쿠픈_생성_함께_사용_할인금액_10000원;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CouponControllerAcceptanceFixture extends IntegrationHelper {

    @Autowired
    protected CouponService couponService;

    @Autowired
    protected CouponRepository couponRepository;

    @Autowired
    protected MemberCouponRepository memberCouponRepository;

    @Autowired
    protected TokenProvider tokenProvider;

    protected CouponCreateRequest 쿠폰_생성서를_만든다() {
        return new CouponCreateRequest("쿠폰명", "내용", false, false, 10);
    }

    protected ExtractableResponse 쿠폰_생성을_한다(final String token, final CouponCreateRequest request, final String url) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(request)
                .post(url)
                .then().log().all()
                .extract();
    }

    protected void 쿠폰_생성_검증(final ExtractableResponse result) {
        String location = result.header("location");

        assertSoftly(softly -> {
            softly.assertThat(location).isEqualTo("/api/coupons/1");
            softly.assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        });
    }

    protected void 멤버_쿠폰_생성() {
        couponRepository.save(쿠픈_생성_독자_사용_할인율_10_퍼센트());
        memberCouponRepository.save(MemberCouponFixture.멤버_쿠폰_생성());
    }

    protected ExtractableResponse 멤버의_쿠폰을_조회한다(final String token, final String url) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 멤버_쿠폰_조회_검증(final ExtractableResponse response) {
        CouponsResponse result = response.body().as(CouponsResponse.class);

        assertSoftly(softly -> {
            softly.assertThat(result.coupons().size()).isEqualTo(1);
            softly.assertThat(result.memberId()).isEqualTo(1L);
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        });
    }

    protected MemberCouponCreateRequest 쿠폰_지급서_생성() {
        couponRepository.save(쿠픈_생성_독자_사용_할인율_10_퍼센트());
        return new MemberCouponCreateRequest(List.of(1L));
    }

    protected ExtractableResponse 멤버에게_쿠폰을_지급한다(final String token, final String url, final MemberCouponCreateRequest request) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(request)
                .post(url)
                .then().log().all()
                .extract();
    }

    protected void 쿠폰_지급_검증(final ExtractableResponse result) {
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    protected void 할인가_10000원_쿠폰_생성() {
        couponRepository.save(쿠픈_생성_함께_사용_할인금액_10000원());
    }

    protected ExtractableResponse 쿠폰을_적용한다(final String token, final String url) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 쿠폰_적용_결과_검증(final ExtractableResponse response) {
        ApplyCouponResponse result = response.as(ApplyCouponResponse.class);

        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(result.originPrice()).isEqualTo(10000);
            softly.assertThat(result.discountPrice()).isEqualTo(0);
            softly.assertThat(result.usingCouponIds()).isEqualTo(List.of(1L));
        });
    }
}
