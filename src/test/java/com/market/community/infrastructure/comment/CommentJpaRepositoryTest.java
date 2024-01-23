package com.market.community.infrastructure.comment;

import com.market.community.domain.comment.Comment;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.market.community.fixture.CommentFixture.댓글_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class CommentJpaRepositoryTest {

    @Autowired
    private CommentJpaRepository commentJpaRepository;

    @Test
    void 댓글을_생성한다() {
        // given
        Comment comment = 댓글_생성();

        // when
        Comment savedComment = commentJpaRepository.save(comment);

        // then
        assertThat(savedComment).usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(comment);
    }

    @Test
    void 댓글을_id로_찾는다() {
        // given
        Comment savedComment = commentJpaRepository.save(댓글_생성());

        // when
        Optional<Comment> found = commentJpaRepository.findById(savedComment.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(found).isPresent();
            softly.assertThat(found.get())
                    .usingRecursiveComparison()
                    .isEqualTo(savedComment);
        });
    }

    @Test
    void 댓글을_id로_제거한다() {
        // given
        Comment savedComment = commentJpaRepository.save(댓글_생성());

        // when
        commentJpaRepository.deleteById(savedComment.getId());

        // then
        Optional<Comment> result = commentJpaRepository.findById(savedComment.getId());
        assertThat(result).isEmpty();
    }
}
