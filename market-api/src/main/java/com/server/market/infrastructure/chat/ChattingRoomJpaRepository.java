package com.server.market.infrastructure.chat;

import com.server.market.domain.chat.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingRoomJpaRepository extends JpaRepository<ChattingRoom, Long> {

    ChattingRoom save(ChattingRoom chattingRoom);
}
