package com.server.community.fixture;

import com.server.community.domain.comment.Comment;

public class CommentFixture {

    public static Comment 댓글_생성() {
        return Comment.builder()
                .id(1L)
                .writerId(1L)
                .boardId(1L)
                .content("내용")
                .build();
    }
}
