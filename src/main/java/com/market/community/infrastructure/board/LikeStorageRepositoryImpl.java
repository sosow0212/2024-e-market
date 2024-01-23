package com.market.community.infrastructure.board;

import com.market.community.domain.board.LikeStorage;
import com.market.community.domain.board.LikeStorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class LikeStorageRepositoryImpl implements LikeStorageRepository {

    private final LikeStorageJpaRepository likeStorageJpaRepository;

    @Override
    public LikeStorage save(final LikeStorage likeStorage) {
        return likeStorageJpaRepository.save(likeStorage);
    }

    @Override
    public boolean existsByBoardIdAndMemberId(final Long boardId, final Long memberId) {
        return likeStorageJpaRepository.existsByBoardIdAndMemberId(boardId, memberId);
    }

    @Override
    public void deleteByBoardIdAndMemberId(final Long boardId, final Long memberId) {
        likeStorageJpaRepository.deleteByBoardIdAndMemberId(boardId, memberId);
    }
}
