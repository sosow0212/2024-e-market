package com.server.market.infrastructure.chat;

import com.server.market.domain.chat.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChattingRoomJpaRepository extends JpaRepository<ChattingRoom, Long> {

    ChattingRoom save(ChattingRoom chattingRoom);

    Optional<ChattingRoom> findBySellerIdAndBuyerIdAndProductId(Long sellerId, Long buyerId, Long productId);

    List<ChattingRoom> findAllByProductId(Long productId);
}
