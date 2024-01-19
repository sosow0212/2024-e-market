package com.market.comment.ui.dto;

import com.market.comment.domain.Comment;

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
