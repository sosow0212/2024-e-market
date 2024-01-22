package com.market.board.application;

import com.market.board.domain.event.LikePushedEvent;
import com.market.board.domain.like.LikeStorage;
import com.market.board.domain.like.LikeStorageRepository;
import com.market.global.event.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeStorageRepository likeStorageRepository;

    @Transactional
    public void patchLike(final Long boardId, final Long memberId) {
        boolean isNeedToIncrease = doesNeedToIncreaseLikeCount(boardId, memberId);
        Events.raise(new LikePushedEvent(boardId, isNeedToIncrease));
    }

    private boolean doesNeedToIncreaseLikeCount(final Long boardId, final Long memberId) {
        if (likeStorageRepository.existsByBoardIdAndMemberId(boardId, memberId)) {
            likeStorageRepository.deleteByBoardIdAndMemberId(boardId, memberId);
            return false;
        }

        likeStorageRepository.save(new LikeStorage(boardId, memberId));
        return true;
    }
}
