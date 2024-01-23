package com.market.community.domain.comment;

import com.market.community.exception.exceptions.CommentWriterNotEqualsException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.market.community.fixture.CommentFixture.댓글_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CommentTest {

    @Test
    void 댓글을_업데이트한다() {
        // given
        Comment comment = 댓글_생성();
        String edit = "edit";

        // when
        comment.update(edit, comment.getWriterId());

        // then
        assertThat(comment.getContent()).isEqualTo(edit);
    }

    @Test
    void 작성자가_다르면_업데이트시_예외를_발생시킨다() {
        // given
        Comment comment = 댓글_생성();
        String edit = "edit";

        // when & then
        assertThatThrownBy(() -> comment.update(edit, comment.getWriterId() + 1))
                .isInstanceOf(CommentWriterNotEqualsException.class);
    }
}
