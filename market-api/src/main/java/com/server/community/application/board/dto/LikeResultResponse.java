package com.server.community.application.board.dto;

public record LikeResultResponse(
        Long boardId,
        boolean likeStatus
) {
}
