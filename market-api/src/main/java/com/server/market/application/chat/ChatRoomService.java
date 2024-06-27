package com.server.market.application.chat;

import com.server.market.application.chat.dto.ChatMessageRequest;
import com.server.market.domain.chat.Chat;
import com.server.market.domain.chat.ChatRepository;
import com.server.market.domain.chat.ChattingRoom;
import com.server.market.domain.chat.ChattingRoomRepository;
import com.server.market.domain.chat.ChattingRooms;
import com.server.market.domain.product.event.ProductSoldEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatRoomService {

    private final ChattingRoomRepository chattingRoomRepository;
    private final ChatRepository chatRepository;

    public ChattingRoom createChattingRoomByBuyer(
            final Long authMember,
            final Long productId,
            final Long sellerId
    ) {
        return chattingRoomRepository.findBySellerIdAndBuyerIdAndProductId(sellerId, authMember, productId)
                .orElseGet(() -> chattingRoomRepository.save(ChattingRoom.createNewChattingRoom(productId, authMember, sellerId)));
    }

    public Chat chat(final Long chatRoomId, final ChatMessageRequest chattingRequest) {
        return chatRepository.save(Chat.of(chatRoomId, chattingRequest.senderId(), chattingRequest.message()));
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = ProductSoldEvent.class)
    public void done(final ProductSoldEvent productSoldEvent) {
        List<ChattingRoom> foundChattingRooms = chattingRoomRepository.findAllByProductId(productSoldEvent.getProductId());
        ChattingRooms chattingRooms = new ChattingRooms(foundChattingRooms);
        chattingRooms.done();
    }
}
