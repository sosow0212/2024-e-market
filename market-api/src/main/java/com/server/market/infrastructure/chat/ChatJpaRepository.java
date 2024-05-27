package com.server.market.infrastructure.chat;

import com.server.market.domain.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatJpaRepository extends JpaRepository<Chat, Long> {

    Chat save(Chat chat);
}
