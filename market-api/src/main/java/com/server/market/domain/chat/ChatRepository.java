package com.server.market.domain.chat;

import com.server.market.domain.chat.dto.ChatHistoryResponse;

import java.util.List;

public interface ChatRepository {

    Chat save(Chat chat);

    List<ChatHistoryResponse> findChattingHistoryByChatId(Long authId, Long chattingRoomId, Long chatId, Integer pageSize);
}
