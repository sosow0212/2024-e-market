package com.server.community.domain.comment.dto;

import java.time.LocalDateTime;

public record CommentSimpleResponse(
        Long id,
        String content,
        Long writerId,
        String writerNickname,
        LocalDateTime createDate
) {
}
