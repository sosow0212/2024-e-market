package com.server.market.application.chat.dto;

public record ChatMessageRequest(
        Long senderId,
        String senderName,
        String message
) {
}
