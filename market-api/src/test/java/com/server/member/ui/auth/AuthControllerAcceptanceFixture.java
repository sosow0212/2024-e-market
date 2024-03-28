package com.server.member.ui.auth;

import com.server.helper.IntegrationHelper;
import com.server.member.application.auth.dto.LoginRequest;
import com.server.member.application.auth.dto.SignupRequest;
import com.server.member.domain.member.Member;
import com.server.member.ui.auth.dto.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;

import static org.assertj.core.api.Assertions.assertThat;

class AuthControllerAcceptanceFixture extends IntegrationHelper {

    protected SignupRequest 회원_가입_데이터를_요청한다() {
        return new SignupRequest("email", "password");
    }

    protected <T> ExtractableResponse 요청한다(final T request, final String url) {
        return RestAssured.given().log().all()
                .body(request)
                .contentType(ContentType.JSON)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    protected void 토큰_생성_검증(final ExtractableResponse actual) {
        var result = actual.as(TokenResponse.class);

        assertThat(result.token()).isNotBlank();
    }

    protected LoginRequest 로그인_데이터를_요청한다(final Member member) {
        return new LoginRequest(member.getEmail(), member.getPassword());
    }
}
