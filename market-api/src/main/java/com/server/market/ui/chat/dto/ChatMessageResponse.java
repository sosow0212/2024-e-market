package com.server.market.ui.chat.dto;

import com.server.market.domain.chat.Chat;

public record ChatMessageResponse(
        Long chattingRoomId,
        Long chatId,
        Long senderId,
        String message
) {

    public static ChatMessageResponse from(final Chat chat) {
        return new ChatMessageResponse(chat.getId(), chat.getChatRoomId(), chat.getSenderId(), chat.getMessage());
    }
}
