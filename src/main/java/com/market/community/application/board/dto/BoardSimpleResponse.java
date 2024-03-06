package com.market.community.application.board.dto;

import java.time.LocalDateTime;

public record BoardSimpleResponse(
        Long id,
        String writerNickname,
        String title,
        LocalDateTime createdDate
) {
}
