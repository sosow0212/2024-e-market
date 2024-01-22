package com.market.board.fixture;

import com.market.board.domain.like.LikeStorage;

public class LikeFixture {

    public static LikeStorage 좋아요_생성() {
        return LikeStorage.builder()
                .boardId(1L)
                .memberId(1L)
                .build();
    }

    public static LikeStorage 좋아요_생성_id_존재() {
        return LikeStorage.builder()
                .id(1L)
                .boardId(1L)
                .memberId(1L)
                .build();
    }
}
