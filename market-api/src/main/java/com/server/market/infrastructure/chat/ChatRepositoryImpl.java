package com.server.market.infrastructure.chat;

import com.server.market.domain.chat.Chat;
import com.server.market.domain.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChatRepositoryImpl implements ChatRepository {

    private final ChatJpaRepository chatJpaRepository;

    @Override
    public Chat save(final Chat chat) {
        return chatJpaRepository.save(chat);
    }
}
