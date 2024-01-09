package com.market.member.ui.auth;

import com.market.helper.IntegrationHelper;
import com.market.member.application.auth.dto.LoginRequest;
import com.market.member.application.auth.dto.SignupRequest;
import com.market.member.domain.auth.TokenProvider;
import com.market.member.domain.member.Member;
import com.market.member.ui.auth.dto.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.function.Executable;

import static org.assertj.core.api.Assertions.assertThat;


class AuthControllerAcceptedTestFixture extends IntegrationHelper {

    protected SignupRequest 회원_가입_데이터를_요청한다() {
        return new SignupRequest("nickname", "email", "password");
    }

    protected <T> ExtractableResponse 요청(final T request, final String url) {
        return RestAssured.given().log().all()
                .body(request)
                .contentType(ContentType.JSON)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    protected <T> Executable 토큰_생성_검증(final ExtractableResponse actual) {
        var result = actual.as(TokenResponse.class);

        return () -> assertThat(result.token()).isNotBlank();
    }

    protected LoginRequest 로그인_데이터를_요청한다(final Member member) {
        return new LoginRequest(member.getEmail(), member.getPassword());
    }
}
