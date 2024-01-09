package com.market.member.application.auth;

import com.market.member.application.auth.dto.LoginRequest;
import com.market.member.application.auth.dto.SignupRequest;
import com.market.member.domain.auth.TokenProvider;
import com.market.member.domain.member.Member;
import com.market.member.domain.member.MemberRepository;
import com.market.member.exception.exceptions.member.MemberAlreadyExistedException;
import com.market.member.exception.exceptions.member.MemberNotFoundException;
import com.market.member.exception.exceptions.member.PasswordNotMatchedException;
import com.market.member.infrastructure.member.MemberFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.market.member.fixture.member.MemberFixture.일반_유저_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private TokenProvider tokenProvider;
    private AuthService authService;
    private MemberRepository memberRepository;

    @BeforeEach
    void setup() {
        memberRepository = new MemberFakeRepository();
        authService = new AuthService(tokenProvider, memberRepository);
    }

    @DisplayName("회원가입을 진행한다")
    @Nested
    class Signup {

        @Test
        void 회원가입을_성공한다() {
            // given
            SignupRequest req = new SignupRequest("nickname", "email", "password");

            String expectedToken = "token";
            when(tokenProvider.create(anyLong())).thenReturn(expectedToken);

            // when
            String result = authService.signup(req);

            // then
            assertThat(result).isEqualTo(expectedToken);
        }

        @Test
        void 이미_존재하는_이메일이라면_예외를_발생한다() {
            // given
            Member existedMember = 일반_유저_생성();
            memberRepository.save(existedMember);

            SignupRequest req = new SignupRequest("nickname", existedMember.getEmail(), "password");

            // when & then
            assertThatThrownBy(() -> authService.signup(req))
                    .isInstanceOf(MemberAlreadyExistedException.class);
        }
    }

    @DisplayName("로그인을 진행한다")
    @Nested
    class Login {

        @Test
        void 로그인을_성공적으로_진행한다() {
            // given
            Member member = memberRepository.save(일반_유저_생성());
            LoginRequest request = new LoginRequest(member.getEmail(), member.getPassword());

            String expectedToken = "token";
            when(tokenProvider.create(any())).thenReturn(expectedToken);

            // when
            String result = authService.login(request);

            // then
            assertThat(result).isEqualTo(expectedToken);
        }

        @Test
        void 존재하지_않는_이메일로_로그인시_예외를_발생한다() {
            // given
            Member member = memberRepository.save(일반_유저_생성());
            String wrongEmail = "wrong";
            LoginRequest request = new LoginRequest(wrongEmail, member.getPassword());

            // when & then
            assertThatThrownBy(() -> authService.login(request))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        void 패스워드가_틀리면_예외를_발생한다() {
            // given
            Member member = memberRepository.save(일반_유저_생성());
            String wrongPassword = "wrong";
            LoginRequest request = new LoginRequest(member.getEmail(), wrongPassword);

            // when & then
            assertThatThrownBy(() -> authService.login(request))
                    .isInstanceOf(PasswordNotMatchedException.class);
        }
    }
}
