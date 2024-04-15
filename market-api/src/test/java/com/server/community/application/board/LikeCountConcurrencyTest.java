package com.server.community.application.board;

import com.server.community.domain.board.Board;
import com.server.community.domain.board.BoardRepository;
import com.server.helper.ConcurrencyHelper;
import com.server.helper.IntegrationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicLong;

import static com.server.community.fixture.BoardFixture.게시글_생성_사진없음;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class LikeCountConcurrencyTest extends IntegrationHelper {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    private Board board;

    @BeforeEach
    void setup() {
        board = boardRepository.save(게시글_생성_사진없음());
    }

    @Test
    void 게시글_좋아요_100번_실행() throws InterruptedException {
        // given
        AtomicLong expectSuccessCount = new AtomicLong();

        // when
        int successThread = ConcurrencyHelper.execute(
                () -> boardService.patchLike(board.getId(), true),
                expectSuccessCount
        );

        // then
        assertThat(expectSuccessCount.get()).isEqualTo(successThread);
    }
}
