package com.server.community.infrastructure.board;

import com.server.community.domain.board.LikeStorage;
import com.server.helper.IntegrationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static com.server.community.fixture.LikeFixture.좋아요_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class LikeStorageJpaRepositoryTest extends IntegrationHelper {

    @Autowired
    private LikeStorageJpaRepository likeStorageJpaRepository;

    private LikeStorage likeStorage;

    @BeforeEach
    void setup() {
        likeStorage = 좋아요_생성();
    }

    @Test
    void 좋아요를_저장한다() {
        // when
        LikeStorage savedLikes = likeStorageJpaRepository.save(likeStorage);

        // then
        assertSoftly(softly -> {
            softly.assertThat(savedLikes.getId()).isEqualTo(1);
            softly.assertThat(savedLikes)
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(likeStorage);
        });
    }

    @Test
    void 멤버_id와_게시글_id로_좋아요가_있는지_조회한다() {
        // given
        LikeStorage savedLikes = likeStorageJpaRepository.save(likeStorage);

        // when
        boolean result = likeStorageJpaRepository.existsByBoardIdAndMemberId(savedLikes.getBoardId(), savedLikes.getMemberId());

        // then
        assertThat(result).isTrue();
    }

    @Transactional
    @Test
    void 멤버_id와_게시글_id로_좋아요를_제거한다() {
        // given
        LikeStorage savedLikes = likeStorageJpaRepository.save(likeStorage);

        // when
        likeStorageJpaRepository.deleteByBoardIdAndMemberId(savedLikes.getBoardId(), savedLikes.getMemberId());

        // then
        boolean result = likeStorageJpaRepository.existsByBoardIdAndMemberId(savedLikes.getBoardId(), savedLikes.getMemberId());
        assertThat(result).isFalse();
    }
}
