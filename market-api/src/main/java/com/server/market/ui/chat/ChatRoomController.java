package com.server.market.ui.chat;

import com.server.market.application.chat.ChatRoomQueryService;
import com.server.market.application.chat.ChatRoomService;
import com.server.market.application.chat.dto.ChatMessageRequest;
import com.server.market.application.chat.dto.ChattingRoomCreateRequest;
import com.server.market.domain.chat.Chat;
import com.server.market.domain.chat.ChattingRoom;
import com.server.market.domain.chat.dto.ChatHistoryResponse;
import com.server.market.domain.chat.dto.ChattingRoomSimpleResponse;
import com.server.market.ui.chat.dto.ChatMessageResponse;
import com.server.member.ui.auth.support.AuthMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChatRoomController {

    private final ChatRoomQueryService chatRoomQueryService;
    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    // 내가 채팅중인 모든 채팅방 반환
    @GetMapping("/api/chats")
    public ResponseEntity<List<ChattingRoomSimpleResponse>> findAllMyChats(@AuthMember final Long authId) {
        return ResponseEntity.ok(chatRoomQueryService.findAllMyChats(authId));
    }

    // 소비자가 채팅 요청 (채팅방 생성)
    @PostMapping("/api/products/{productId}/chats")
    public ResponseEntity<Void> createChattingRoomByBuyer(
            @PathVariable final Long productId,
            @AuthMember final Long authMember,
            @RequestBody @Valid final ChattingRoomCreateRequest request
    ) {
        ChattingRoom chattingRoom = chatRoomService.createChattingRoomByBuyer(authMember, request.sellerId(), productId);
        return ResponseEntity.created(URI.create("/api/products/" + productId + "/chats/" + chattingRoom.getId()))
                .build();
    }

    // 채팅방 채팅 내역 반환
    @GetMapping("/api/products/{productId}/chats/{chattingRoomId}")
    public ResponseEntity<List<ChatHistoryResponse>> findChattingHistoryByChatId(
            @AuthMember final Long authId,
            @PathVariable final Long productId,
            @PathVariable final Long chattingRoomId,
            @RequestParam(name = "chatId", required = false) final Long chatId,
            @RequestParam(name = "pageSize") final Integer pageSize
    ) {
        return ResponseEntity.ok(chatRoomQueryService.findChattingHistoryByChatId(authId, chattingRoomId, chatId, pageSize));
    }

    // linking url : ws://localhost:8080/ws-stomp
    // subscribe url : ws://localhost:8080/ws-stomp/sub/chats/1
    // publish url : ws://localhost:8080/ws-stomp/pub/chats/1/messages
    @MessageMapping("/chats/{chatRoomId}/messages")
    public ChatMessageResponse chat(
            @DestinationVariable final Long chatRoomId,
            final ChatMessageRequest chattingRequest
    ) {
        Chat chat = chatRoomService.chat(chatRoomId, chattingRequest);
        messagingTemplate.convertAndSend("/sub/chats/" + chatRoomId, chattingRequest);
        return ChatMessageResponse.from(chat);
    }
}