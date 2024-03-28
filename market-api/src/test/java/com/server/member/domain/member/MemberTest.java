package com.server.member.domain.member;

import com.server.member.exception.exceptions.member.PasswordNotMatchedException;
import com.server.member.infrastructure.member.NicknameFakeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.server.member.domain.member.Member.createDefaultRole;
import static com.server.member.fixture.member.MemberFixture.어드민_유저_생성;
import static com.server.member.fixture.member.MemberFixture.일반_유저_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberTest {

    private NicknameGenerator nicknameGenerator;

    @BeforeEach
    void setup() {
        nicknameGenerator = new NicknameFakeGenerator();
    }

    @Test
    void 어드민인_경우에_true를_반환한다() {
        // given
        Member admin = 어드민_유저_생성();

        // when
        boolean result = admin.isAdmin();

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 패스워드가_다른_경우에_예외를_발생한다() {
        // given
        Member member = 일반_유저_생성();
        String givenPassword = "wrongPassword";

        // when & then
        assertThatThrownBy(() -> member.validatePassword(givenPassword))
                .isInstanceOf(PasswordNotMatchedException.class);
    }

    @Test
    void 회원가입시_기본적으로_MEMBER_ROLE과_랜덤한_닉네임으로_생성된다() {
        // when
        Member member = createDefaultRole("email@email.com", "password", nicknameGenerator);

        // then
        assertSoftly(softly -> {
            softly.assertThat(member.getMemberRole()).isEqualTo(MemberRole.MEMBER);
            softly.assertThat(member.getNickname()).isEqualTo("nickname");
        });
    }

    @Test
    void 멤버가_일치하는지_확인한다() {
        // given
        Member member = Member.builder()
                .id(1L)
                .build();

        // when & then
        assertDoesNotThrow(() -> member.validateAuth(member.getId()));
    }
}
