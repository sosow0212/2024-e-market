package com.server.community.ui.comment.dto;

import com.server.community.domain.comment.Comment;

public record CommentResponse(

        Long commentId,
        Long boardId,
        Long writerId,
        String comment
) {

    public static CommentResponse from(final Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getBoardId(),
                comment.getWriterId(),
                comment.getContent()
        );
    }
}
