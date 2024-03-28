package com.server.member.ui.auth;

import com.server.member.domain.member.Member;
import com.server.member.domain.member.MemberRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.server.member.fixture.member.MemberFixture.일반_유저_생성;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AuthControllerAcceptanceTest extends AuthControllerAcceptanceFixture {

    private static final String 회원가입_url = "/api/signup";
    private static final String 로그인_url = "/api/login";

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원가입을_진행한다() {
        // given
        var 회원가입_요청_데이터 = 회원_가입_데이터를_요청한다();

        // when
        var 회원가입_결과 = 요청한다(회원가입_요청_데이터, 회원가입_url);

        // then
        토큰_생성_검증(회원가입_결과);
    }

    @Test
    void 로그인을_진행한다() {
        // given
        var 회원 = 회원_생성();
        var 로그인_요청_데이터 = 로그인_데이터를_요청한다(회원);

        // when
        var 로그인_결과 = 요청한다(로그인_요청_데이터, 로그인_url);

        // then
        토큰_생성_검증(로그인_결과);
    }

    private Member 회원_생성() {
        return memberRepository.save(일반_유저_생성());
    }
}
