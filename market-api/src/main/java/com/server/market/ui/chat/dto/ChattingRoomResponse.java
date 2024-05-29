package com.server.market.ui.chat.dto;

import com.server.market.domain.chat.ChattingRoom;
import com.server.market.domain.chat.vo.ChattingStatus;

public record ChattingRoomResponse(
        Long chatRoomId,
        Long productId,
        Long buyerId,
        Long sellerId,
        ChattingStatus chattingStatus
) {

    public static ChattingRoomResponse from(final ChattingRoom chattingRoom) {
        return new ChattingRoomResponse(chattingRoom.getId(), chattingRoom.getProductId(), chattingRoom.getBuyerId(), chattingRoom.getSellerId(), chattingRoom.getChattingStatus());
    }
}
