package com.server.market.infrastructure.chat;

import com.server.market.domain.chat.Chat;
import com.server.market.domain.chat.ChatRepository;
import com.server.market.domain.chat.dto.ChatHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ChatRepositoryImpl implements ChatRepository {

    private final ChatJpaRepository chatJpaRepository;
    private final ChatQueryRepository chatQueryRepository;

    @Override
    public Chat save(final Chat chat) {
        return chatJpaRepository.save(chat);
    }

    @Override
    public List<ChatHistoryResponse> findChattingHistoryByChatId(final Long authId, final Long chattingRoomId, final Long chatId, final Integer pageSize) {
        return chatQueryRepository.findChattingHistoryByChatId(authId, chattingRoomId, chatId, pageSize);
    }
}
