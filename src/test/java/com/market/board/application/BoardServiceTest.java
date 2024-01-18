package com.market.board.application;

import com.market.board.application.dto.BoardCreateRequest;
import com.market.board.application.dto.BoardUpdateRequest;
import com.market.board.domain.board.Board;
import com.market.board.domain.board.BoardRepository;
import com.market.board.domain.image.ImageConverter;
import com.market.board.exception.exceptions.BoardNotFoundException;
import com.market.board.exception.exceptions.WriterNotEqualsException;
import com.market.board.infrastructure.board.BoardFakeRepository;
import com.market.board.infrastructure.image.ImageFakeConverter;
import com.market.board.infrastructure.image.ImageFakeUploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.market.board.fixture.BoardFixture.게시글_생성_사진없음;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class BoardServiceTest {

    private BoardService boardService;
    private BoardRepository boardRepository;
    private ImageConverter imageConverter;
    private ImageUploader imageUploader;

    @BeforeEach
    void setup() {
        boardRepository = new BoardFakeRepository();
        imageConverter = new ImageFakeConverter();
        imageUploader = new ImageFakeUploader();

        boardService = new BoardService(
                boardRepository,
                imageConverter,
                imageUploader
        );
    }

    @Test
    void 게시글을_저장한다() {
        // given
        Long memberId = 1L;
        BoardCreateRequest req = new BoardCreateRequest("title", "content", new ArrayList<>());

        // when
        Long result = boardService.saveBoard(memberId, req);

        // then
        assertThat(result).isEqualTo(1L);
    }

    @Test
    void 게시글을_찾는다() {
        // given
        Board savedBoard = boardRepository.save(게시글_생성_사진없음());

        // when
        Board result = boardService.findBoardById(savedBoard.getId());

        // then
        assertThat(savedBoard).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    void 게시글이_없으면_에외를_발생시킨다() {
        // when & then
        assertThatThrownBy(() -> boardService.findBoardById(1L))
                .isInstanceOf(BoardNotFoundException.class);
    }

    @Test
    void 게시글을_수정한다() {
        // given
        Board savedBoard = boardRepository.save(게시글_생성_사진없음());

        Long boardId = savedBoard.getId();
        Long memberId = savedBoard.getWriterId();
        MockMultipartFile file = new MockMultipartFile("name", "origin.jpg", "image", "content".getBytes());
        BoardUpdateRequest req = new BoardUpdateRequest("수정", "수정", new ArrayList<>(List.of(file)), new ArrayList<>());

        // when & then
        boardService.patchBoardById(boardId, memberId, req);

        // then
        assertSoftly(softly -> {
            softly.assertThat(savedBoard.getPost().getTitle()).isEqualTo("수정");
            softly.assertThat(savedBoard.getPost().getContent()).isEqualTo("수정");
        });
    }

    @Test
    void 게시글이_없다면_수정하지_못한다() {
        // given
        BoardUpdateRequest req = new BoardUpdateRequest("수정", "수정", new ArrayList<>(), new ArrayList<>());

        // when & then
        assertThatThrownBy(() -> boardService.patchBoardById(1L, 1L, req))
                .isInstanceOf(BoardNotFoundException.class);
    }

    @Test
    void 게시글_주인이_다르면_수정하지_못한다() {
        // given
        Board savedBoard = boardRepository.save(게시글_생성_사진없음());
        BoardUpdateRequest req = new BoardUpdateRequest("수정", "수정", new ArrayList<>(), new ArrayList<>());

        // when & then
        assertThatThrownBy(() -> boardService.patchBoardById(savedBoard.getId(), savedBoard.getWriterId() + 1, req))
                .isInstanceOf(WriterNotEqualsException.class);
    }

    @Test
    void 게시글을_삭제한다() {
        // given
        Board savedBoard = boardRepository.save(게시글_생성_사진없음());

        // when & then
        assertDoesNotThrow(() -> boardService.deleteBoardById(savedBoard.getId(), savedBoard.getWriterId()));
    }

    @Test
    void 게시글_주인이_다르다면_삭제하지_못한다() {
        // given
        Board savedBoard = boardRepository.save(게시글_생성_사진없음());

        // when & then
        assertThatThrownBy(() -> boardService.deleteBoardById(savedBoard.getId(), savedBoard.getWriterId() + 1))
                .isInstanceOf(WriterNotEqualsException.class);
    }
}
