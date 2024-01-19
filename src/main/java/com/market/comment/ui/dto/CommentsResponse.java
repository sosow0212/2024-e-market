package com.market.comment.ui.dto;

import com.market.comment.domain.Comment;

import java.util.List;
import java.util.stream.Collectors;

public record CommentsResponse(
        List<CommentResponse> comments
) {

    public static CommentsResponse from(final List<Comment> comments) {
        return comments.stream()
                .map(CommentResponse::from)
                .collect(Collectors.collectingAndThen(Collectors.toList(), CommentsResponse::new));
    }
}
