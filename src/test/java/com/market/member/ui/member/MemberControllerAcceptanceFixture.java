package com.market.member.ui.member;

import com.market.helper.IntegrationHelper;
import com.market.member.domain.auth.TokenProvider;
import com.market.member.domain.member.Member;
import com.market.member.domain.member.MemberRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import static com.market.member.fixture.member.MemberFixture.일반_유저_생성;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberControllerAcceptanceFixture extends IntegrationHelper {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    protected Member 멤버_생성() {
        return memberRepository.save(일반_유저_생성());
    }

    protected String 토큰_생성(final Long id) {
        return tokenProvider.create(id);
    }

    protected ExtractableResponse 판매_내역을_요청한다(final String token, final String url) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 판매_내역_조회_결과_검증(final ExtractableResponse response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
