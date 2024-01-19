package com.market.comment.infrastructure;

import com.market.comment.domain.Comment;
import com.market.helper.IntegrationHelper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.market.comment.fixture.CommentFixture.댓글_생성;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CommentJdbcRepositoryTest extends IntegrationHelper {

    @Autowired
    private CommentJpaRepository commentJpaRepository;

    @Autowired
    private CommentJdbcRepository commentJdbcRepository;

    @Test
    void 게시글_id에_해당되는_것을_모두_지운다() {
        // given
        Comment savedComment = commentJpaRepository.save(댓글_생성());

        // when
        commentJdbcRepository.deleteAllCommentsByBoardId(savedComment.getBoardId());

        // then
        Optional<Comment> result = commentJpaRepository.findById(1L);
        assertThat(result).isEmpty();
    }
}
