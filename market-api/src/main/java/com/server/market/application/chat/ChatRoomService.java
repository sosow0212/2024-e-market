package com.server.market.application.chat;

import com.server.market.application.chat.dto.ChatMessageRequest;
import com.server.market.domain.chat.Chat;
import com.server.market.domain.chat.ChatRepository;
import com.server.market.domain.chat.ChattingRoom;
import com.server.market.domain.chat.ChattingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChatRoomService {

    private final ChattingRoomRepository chattingRoomRepository;
    private final ChatRepository chatRepository;

    @Transactional
    public ChattingRoom createChattingRoomByBuyer(final Long authMember, final Long productId, final Long sellerId) {
        ChattingRoom chattingRoom = ChattingRoom.createNewChattingRoom(productId, authMember, sellerId);
        return chattingRoomRepository.save(chattingRoom);
    }

    @Transactional
    public Chat chat(final Long chatRoomId, final ChatMessageRequest chattingRequest) {
        return chatRepository.save(Chat.of(chatRoomId, chattingRequest.senderId(), chattingRequest.message()));
    }
}
