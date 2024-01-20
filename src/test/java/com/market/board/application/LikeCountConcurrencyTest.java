package com.market.board.application;

import com.market.board.domain.board.Board;
import com.market.board.domain.board.BoardRepository;
import com.market.helper.IntegrationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.market.board.fixture.BoardFixture.게시글_생성_사진없음;
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

    //    @Test
    void 게시글_좋아요_100번_실행() throws Exception {
        // given
        long startTime = System.currentTimeMillis();
        int thread = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(thread);

        // when
        for (int i = 0; i < thread; i++) {
            executorService.submit(() -> {
                try {
                    boardService.patchLike(board.getId(), true);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // then
        Double sec = (System.currentTimeMillis() - startTime) / 1000.0;
        System.out.printf("thread 100개, 소요시간 --- (%.2f초)%n", sec);

        Board result = boardRepository.findById(board.getId()).get();
        assertThat(result.getLikeCount().getLikeCount()).isEqualTo(thread);
    }
}
