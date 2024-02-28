package com.market.member.ui.member;

import com.market.member.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberControllerAcceptanceTest extends MemberControllerAcceptanceFixture {

    private static final String 판매_내역_조회_요청_url = "/api/members/1/histories?isSeller=true";

    private Member 멤버;
    private String 토큰;

    @BeforeEach
    void setup() {
        멤버 = 멤버_생성();
        토큰 = 토큰_생성(멤버.getId());
    }

    @Test
    void 유저의_판매_내역을_조회한다() {
        // when
        var 판매_내역_조회_결과 = 판매_내역을_요청한다(토큰, 판매_내역_조회_요청_url);

        // then
        판매_내역_조회_결과_검증(판매_내역_조회_결과);
    }
}
