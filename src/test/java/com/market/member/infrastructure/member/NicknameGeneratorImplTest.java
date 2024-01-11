package com.market.member.infrastructure.member;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class NicknameGeneratorImplTest {

    private final NicknameGeneratorImpl nicknameGenerator = new NicknameGeneratorImpl();

    @Test
    void 닉네임이_성공적으로_생성된다() {
        // when
        String createdNickname = nicknameGenerator.createRandomNickname();

        // then
        assertThat(createdNickname).isNotBlank();
    }

}
