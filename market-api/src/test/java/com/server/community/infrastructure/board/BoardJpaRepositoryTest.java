package com.server.community.infrastructure.board;

import com.server.community.domain.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.server.community.fixture.BoardFixture.게시글_생성_사진없음;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DataJpaTest
class BoardJpaRepositoryTest {

    @Autowired
    private BoardJpaRepository boardJpaRepository;

    private Board board;

    @BeforeEach
    void setup() {
        board = 게시글_생성_사진없음();
    }

    @Test
    void 게시글을_저장한다() {
        // when
        Board saved = boardJpaRepository.save(board);

        // then
        assertThat(saved).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(board);
    }

    @Test
    void 게시글을_id로_단건조회한다() {
        // given
        Board saved = boardJpaRepository.save(board);

        // when
        Optional<Board> result = boardJpaRepository.findById(saved.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).isPresent();
            softly.assertThat(result.get()).usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(board);
        });
    }

    @Test
    void 게시글을_id로_비관적락을_걸어_단건조회한다() {
        // given
        Board saved = boardJpaRepository.save(board);

        // when
        Optional<Board> result = boardJpaRepository.findByIdUsingPessimistic(saved.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).isPresent();
            softly.assertThat(result.get()).usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(board);
        });
    }

    @Test
    void 게시글을_id로_이미지와_함께_단건조회한다() {
        // given
        Board saved = boardJpaRepository.save(board);

        // when
        Optional<Board> result = boardJpaRepository.findBoardWithImages(saved.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).isPresent();
            softly.assertThat(result.get()).usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(board);
        });
    }

    @Test
    void 게시글을_제거한다() {
        // given
        Board saved = boardJpaRepository.save(board);

        // when
        boardJpaRepository.deleteById(saved.getId());

        // then
        assertThat(boardJpaRepository.findById(1L)).isEmpty();
    }
}
