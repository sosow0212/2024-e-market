package com.server.market.domain.chat;

import com.server.market.domain.chat.dto.ChattingRoomSimpleResponse;

import java.util.List;

public interface ChattingRoomRepository {

    ChattingRoom save(ChattingRoom chattingRoom);

    List<ChattingRoomSimpleResponse> findMyChattingRooms(Long authId);
}
