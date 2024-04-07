package com.server.coupon.ui.coupon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CouponControllerAcceptanceTest extends CouponControllerAcceptanceFixture {

    private static final String 쿠폰_생성_url = "/api/coupons";
    private static final String 멤버의_쿠폰_조회_url = "/api/members/1/coupons";
    private static final String 멤버_쿠폰_지급_url = "/api/members/1/coupons";
    private static final String 상품가10000원에_1번_쿠폰_적용_url = "/api/coupons?couponIds=1&price=10000";

    private String 토큰;

    @BeforeEach
    void setup() {
        토큰 = tokenProvider.create(1L);
    }

    @Test
    void 쿠폰을_생성한다() {
        // given
        var 쿠폰_생성_요청서 = 쿠폰_생성서를_만든다();

        // when
        var 쿠폰_생성_결과 = 쿠폰_생성을_한다(토큰, 쿠폰_생성_요청서, 쿠폰_생성_url);

        // then
        쿠폰_생성_검증(쿠폰_생성_결과);
    }

    @Test
    void 멤버가_가진_쿠폰을_조회한다() {
        // given
        멤버_쿠폰_생성();

        // when
        var 멤버_쿠폰_조회_결과 = 멤버의_쿠폰을_조회한다(토큰, 멤버의_쿠폰_조회_url);

        // then
        멤버_쿠폰_조회_검증(멤버_쿠폰_조회_결과);
    }

    @Test
    void 멤버에게_쿠폰을_지급한다() {
        // given
        var 쿠폰_지급서 = 쿠폰_지급서_생성();

        // when
        var 쿠폰_지급_결과 = 멤버에게_쿠폰을_지급한다(토큰, 멤버_쿠폰_지급_url, 쿠폰_지급서);

        // then
        쿠폰_지급_검증(쿠폰_지급_결과);
    }

    @Test
    void 쿠폰을_적용한다() {
        // given
        할인가_10000원_쿠폰_생성();

        // when
        var 쿠폰_적용_결과 = 쿠폰을_적용한다(토큰, 상품가10000원에_1번_쿠폰_적용_url);

        // then
        쿠폰_적용_결과_검증(쿠폰_적용_결과);
    }
}
