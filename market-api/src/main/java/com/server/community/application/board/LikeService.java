package com.server.community.application.board;

import com.server.community.domain.board.LikeStorage;
import com.server.community.domain.board.LikeStorageRepository;
import com.server.community.domain.event.LikePushedEvent;
import com.server.global.event.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeStorageRepository likeStorageRepository;

    @Transactional
    public boolean patchLike(final Long boardId, final Long memberId) {
        boolean isNeedToIncrease = doesNeedToIncreaseLikeCount(boardId, memberId);
        Events.raise(new LikePushedEvent(boardId, isNeedToIncrease));
        return isNeedToIncrease;
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
