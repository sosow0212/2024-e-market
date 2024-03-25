package com.market.community.application.board;

import com.market.community.application.board.dto.BoardFoundResponse;
import com.market.community.application.board.dto.BoardsSimpleResponse;
import com.market.community.domain.board.Board;
import com.market.community.domain.board.BoardRepository;
import com.market.community.exception.exceptions.BoardNotFoundException;
import com.market.community.infrastructure.board.BoardFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import static com.market.community.fixture.BoardFixture.게시글_생성_사진없음;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class BoardQueryServiceTest {

    private BoardQueryService boardQueryService;
    private BoardRepository boardRepository;

    @BeforeEach
    void setup() {
        boardRepository = new BoardFakeRepository();
        boardQueryService = new BoardQueryService(boardRepository);
    }

    @Test
    void 게시글을_모두_조회한다() {
        // when
        BoardsSimpleResponse result = boardQueryService.findAllBoards(PageRequest.of(0, 10));

        // then
        assertThat(result.nextPage()).isEqualTo(-1);
    }

    @Test
    void 게시글을_찾는다() {
        // given
        Board savedBoard = boardRepository.save(게시글_생성_사진없음());

        // when
        BoardFoundResponse result = boardQueryService.findBoardById(savedBoard.getId(), 1L);

        // then
        assertSoftly(softly -> {
            softly.assertThat(result.id()).isEqualTo(savedBoard.getId());
            softly.assertThat(result.title()).isEqualTo(savedBoard.getPost().getTitle());
            softly.assertThat(result.content()).isEqualTo(savedBoard.getPost().getContent());
            softly.assertThat(result.likeCount()).isEqualTo(savedBoard.getLikeCount().getLikeCount());
            softly.assertThat(result.isMyPost()).isEqualTo(true);
        });
    }

    @Test
    void 게시글이_없으면_에외를_발생시킨다() {
        // when & then
        assertThatThrownBy(() -> boardQueryService.findBoardById(1L, 1L))
                .isInstanceOf(BoardNotFoundException.class);
    }
}
