package com.market.board.application;

import com.market.board.domain.board.Board;
import com.market.board.domain.board.BoardRepository;
import com.market.board.domain.like.LikeStorageRepository;
import com.market.helper.IntegrationHelper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.market.board.fixture.BoardFixture.게시글_생성_사진없음;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class BoardEventHandlerTest extends IntegrationHelper {

    @MockBean
    private BoardEventHandler boardEventHandler;

    @MockBean
    private BoardRepository boardRepository;

    @MockBean
    private LikeStorageRepository likeStorageRepository;

    @Autowired
    private LikeService likeService;

    @Test
    void 회원가입_이벤트가_발행되면_구독자가_동작한다() {
        // given
        Board board = 게시글_생성_사진없음();
        when(likeStorageRepository.existsByBoardIdAndMemberId(any(), any())).thenReturn(false);
        when(boardRepository.findById(any())).thenReturn(Optional.of(board));

        // when
        likeService.patchLike(1L, 1L);

        // then
        verify(boardEventHandler).patchLike(any());
    }
}
