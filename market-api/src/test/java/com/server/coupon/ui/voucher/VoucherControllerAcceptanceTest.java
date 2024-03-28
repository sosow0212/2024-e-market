package com.server.coupon.ui.voucher;

import com.server.coupon.domain.coupon.Coupon;
import com.server.coupon.domain.voucher.Voucher;
import com.server.member.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class VoucherControllerAcceptanceTest extends VoucherControllerAcceptanceFixture {

    private static final String 바우처_생성_url = "/api/vouchers";
    private static final String 바우처_사용_url = "/api/vouchers/1";
    private static final String 바우처_페이징_조회_url = "/api/vouchers?page=0?size=10";
    private static final String 바우처_상세_조회_url = "/api/vouchers/1";

    private Member 멤버;
    private Coupon 쿠폰;
    private Voucher 바우처;
    private String 토큰;

    @BeforeEach
    void setup() {
        멤버 = 멤버_생성();
        쿠폰 = 쿠폰_생성();
        토큰 = 토큰_생성();
    }

    @Test
    void 바우처를_생성한다() {
        // given
        var 바우처 = 바우처_생성_요청서();

        // when
        var 바우처_생성_결과 = 바우처_생성_요청(바우처_생성_url, 바우처, 토큰);

        // then
        바우처_생성_검증(바우처_생성_결과);
    }

    @Test
    void 바우처를_사용한다() {
        // given
        var 바우처 = 바우처_생성();
        var 바우처_번호 = 바우처_사용_요청서_생성(바우처);

        // when
        var 바우처_사용_요청 = 바우처_사용_요청(바우처_사용_url, 바우처_번호, 토큰);

        // then
        바우처_사용_검증(바우처_사용_요청);
    }

    @Test
    void 바우처를_페이징_조회한다() {
        // given
        바우처_생성();

        // when
        var 바우처_페이징_조회 = 바우처_페이징_조회_요청(바우처_페이징_조회_url, 토큰);

        // then
        바우처_페이징_조회_검증(바우처_페이징_조회);
    }

    @Test
    void 바우처를_상세_조회한다() {
        // given
        바우처_생성();

        // when
        var 바우처_조회 = 바우처_상세_조회_요청(바우처_상세_조회_url, 토큰);

        // then
        바우처_상세_조회_검증(바우처_조회);
    }
}
