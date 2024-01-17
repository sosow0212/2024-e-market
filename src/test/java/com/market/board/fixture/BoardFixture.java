package com.market.board.fixture;

import com.market.board.domain.board.Board;
import com.market.board.domain.board.Post;

import java.util.ArrayList;

public class BoardFixture {

    public static Board 게시글_생성_사진없음() {
        return Board.builder()
                .post(Post.of("제목", "내용"))
                .writerId(1L)
                .images(new ArrayList<>())
                .build();
    }

    public static Board 게시글_생성_사진없음_id있음() {
        return Board.builder()
                .id(1L)
                .post(Post.of("제목", "내용"))
                .writerId(1L)
                .images(new ArrayList<>())
                .build();
    }

    public static Board 게시글_생성_사진있음() {
        return Board.builder()
                .post(Post.of("제목", "내용"))
                .writerId(1L)
                .images(new ArrayList<>())
                .build();
    }
}
