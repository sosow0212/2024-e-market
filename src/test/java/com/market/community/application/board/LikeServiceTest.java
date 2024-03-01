package com.market.community.application.board;

import com.market.community.domain.board.LikeStorageRepository;
import com.market.community.infrastructure.board.LikeStorageFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class LikeServiceTest {

    private LikeService likeService;

    @BeforeEach
    void setup() {
        LikeStorageRepository likeStorageRepository = new LikeStorageFakeRepository();
        likeService = new LikeService(likeStorageRepository);
    }

    @Test
    void 게시글_좋아요_처리를_한다() {
        // when
        boolean result = likeService.patchLike(1L, 1L);

        // then
        assertThat(result).isTrue();
    }
}
