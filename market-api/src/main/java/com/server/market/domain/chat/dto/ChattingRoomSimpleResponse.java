package com.server.market.domain.chat.dto;

import java.time.LocalDateTime;

public record ChattingRoomSimpleResponse(
        String productName,
        Long productId,
        Long chattingRoomId,
        Long sellerId,
        String sellerNickname,
        LocalDateTime lastChattingTime
) {
}
