package com.server.community.fixture;

import com.server.community.domain.board.LikeStorage;

public class LikeFixture {

    public static LikeStorage 좋아요_생성() {
        return LikeStorage.builder()
                .boardId(1L)
                .memberId(1L)
                .build();
    }
}
