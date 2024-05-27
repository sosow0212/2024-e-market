package com.server.market.application.chat;

import com.server.market.application.chat.dto.ChatMessageRequest;
import com.server.market.domain.chat.Chat;
import com.server.market.domain.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRepository chatRepository;

//    public Chat saveMessage(final Long chatRoomId, final ChatMessageRequest chatMessageRequest) {
//        return chatRepository.save(Chat.builder()
//                .chatRoomId(chatRoomId)
//                .message(chatMessageRequest.message())
//                .build());
//    }
}
