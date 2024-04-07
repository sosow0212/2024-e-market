package com.server.community.domain.board;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.server.community.fixture.ImageFixture.이미지를_생성한다;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ImageTest {

    @Test
    void 이미지의_아이디와_다르면_false를_반환한다() {
        // given
        Image image = 이미지를_생성한다();

        // when
        boolean result = image.isSameImageId(image.getId() + 1);

        // then
        assertThat(result).isFalse();
    }
}
