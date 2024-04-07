package com.server.community.infrastructure.board;

import com.server.community.domain.board.LikeStorage;
import com.server.community.domain.board.LikeStorageRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LikeStorageFakeRepository implements LikeStorageRepository {

    private final Map<Long, LikeStorage> map = new HashMap<>();
    private Long id = 1L;

    @Override
    public LikeStorage save(final LikeStorage likeStorage) {
        LikeStorage savedLikeStorage = LikeStorage.builder()
                .id(id)
                .boardId(likeStorage.getBoardId())
                .memberId(likeStorage.getMemberId())
                .build();

        map.put(id, savedLikeStorage);
        id++;

        return savedLikeStorage;
    }

    @Override
    public boolean existsByBoardIdAndMemberId(final Long boardId, final Long memberId) {
        return map.values().stream()
                .anyMatch(it -> it.getBoardId().equals(boardId) && it.getMemberId().equals(memberId));
    }

    @Override
    public void deleteByBoardIdAndMemberId(final Long boardId, final Long memberId) {
        List<LikeStorage> deleted = map.values().stream()
                .filter(it -> it.getBoardId().equals(boardId) && it.getMemberId().equals(memberId))
                .toList();

        deleted.forEach(it -> map.remove(it.getId()));
    }
}
