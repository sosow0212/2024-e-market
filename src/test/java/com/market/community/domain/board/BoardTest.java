package com.market.community.domain.board;

import com.market.community.domain.board.dto.BoardUpdateResult;
import com.market.community.exception.exceptions.WriterNotEqualsException;
import com.market.community.infrastructure.board.ImageConverterImpl;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.market.community.fixture.BoardFixture.게시글_생성_사진없음;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class BoardTest {

    @Test
    void 게시글을_업데이트한다() {
        // given
        Board board = 게시글_생성_사진없음();

        // when
        BoardUpdateResult result = board.update("수정", "수정", new ArrayList<>(), new ArrayList<>(), new ImageConverterImpl());

        // then
        assertSoftly(softly -> {
            softly.assertThat(result.addedImages()).isEmpty();
            softly.assertThat(result.deletedImages()).isEmpty();
            softly.assertThat(board.getPost().getTitle()).isEqualTo("수정");
            softly.assertThat(board.getPost().getContent()).isEqualTo("수정");
        });
    }

    @Test
    void 작성자가_아니면_예외를_발생한다() {
        // given
        Board board = 게시글_생성_사진없음();

        // when & then
        assertThatThrownBy(() -> board.validateWriter(board.getWriterId() + 1))
                .isInstanceOf(WriterNotEqualsException.class);
    }
}
