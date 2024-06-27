package com.server.market.application.chat;

import com.server.market.domain.chat.ChatRepository;
import com.server.market.domain.chat.ChattingRoomRepository;
import com.server.market.domain.chat.dto.ChatHistoryResponse;
import com.server.market.domain.chat.dto.ChattingRoomSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChatRoomQueryService {

    private final ChatRepository chatRepository;
    private final ChattingRoomRepository chattingRoomRepository;

    public List<ChattingRoomSimpleResponse> findAllMyChats(final Long authId) {
        return chattingRoomRepository.findMyChattingRooms(authId);
    }

    public List<ChatHistoryResponse> findChattingHistoryByChatId(
            final Long authId,
            final Long chattingRoomId,
            final Long chatId,
            final Integer pageSize
    ) {
        return chatRepository.findChattingHistoryByChatId(authId, chattingRoomId, chatId, pageSize);
    }
}
