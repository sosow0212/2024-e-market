package com.server.market.ui.chat;

import com.server.market.application.chat.ChatRoomService;
import com.server.market.application.chat.dto.ChatMessageRequest;
import com.server.market.domain.chat.Chat;
import com.server.member.ui.auth.support.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/api/chats")
    public List<Chat> findMyChats(@AuthMember final Long authId) {
        return null;
    }

    @PostMapping("/api/chats")
    public void createChat() {
        return;
    }

    @GetMapping("/api/chats/{chatId}")
    public void findChatById(
            @PathVariable final Long chatId,
            @AuthMember final Long authId
    ) {

    }

    // linking url : ws://localhost:8080/ws-stomp
    // subscribe url : ws://localhost:8080/ws-stomp/sub/products/1(productId)
    // publish url : ws://localhost:8080/ws-stomp/pub/products/1/messages
    @MessageMapping("/products/{productId}/messages")
    public void chat(
            @DestinationVariable final Long productId,
            final ChatMessageRequest chattingRequest
    ) {
        messagingTemplate.convertAndSend("/sub/products/" + productId, chattingRequest.message());
    }
}
