package com.market.community.domain.board.vo;

import com.market.community.exception.exceptions.LikeCountNegativeNumberException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class LikeCountTest {

    @Test
    void 좋아요_수를_증가시킨다() {
        // given
        LikeCount likeCount = LikeCount.createDefault();

        // when
        likeCount.patchLike(true);

        // then
        assertThat(likeCount.getLikeCount()).isEqualTo(1);
    }

    @Test
    void 좋아요_수는_음수가_될_수_없다() {
        // given
        LikeCount likeCount = LikeCount.createDefault();

        // when & then
        assertThatThrownBy(() -> likeCount.patchLike(false))
                .isInstanceOf(LikeCountNegativeNumberException.class);
    }
}
