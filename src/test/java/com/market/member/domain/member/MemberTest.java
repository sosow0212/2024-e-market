package com.market.member.domain.member;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.market.member.fixture.member.MemberFixture.어드민_유저_생성;
import static com.market.member.fixture.member.MemberFixture.일반_유저_생성;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberTest {

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
    void 패스워드가_다른_경우에_false를_반환한다() {
        // given
        Member member = 일반_유저_생성();
        String givenPassword = "wrongPassword";

        // when
        boolean result = member.hasSamePassword(givenPassword);

        // then
        assertThat(result).isFalse();
    }
}
