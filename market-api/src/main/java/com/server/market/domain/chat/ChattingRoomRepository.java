package com.server.market.domain.chat;

import com.server.market.domain.chat.dto.ChattingRoomSimpleResponse;

import java.util.List;
import java.util.Optional;

public interface ChattingRoomRepository {

    ChattingRoom save(ChattingRoom chattingRoom);

    List<ChattingRoomSimpleResponse> findMyChattingRooms(Long authId);

    Optional<ChattingRoom> findBySellerIdAndBuyerIdAndProductId(Long sellerId, Long buyerId, Long productId);

    List<ChattingRoom> findAllByProductId(Long productId);
}
