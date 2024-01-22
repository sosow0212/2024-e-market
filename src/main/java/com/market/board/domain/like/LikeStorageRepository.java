package com.market.board.domain.like;

public interface LikeStorageRepository {

    LikeStorage save(final LikeStorage likeStorage);

    boolean existsByBoardIdAndMemberId(final Long boardId, final Long memberId);

    void deleteByBoardIdAndMemberId(final Long boardId, final Long memberId);
}
