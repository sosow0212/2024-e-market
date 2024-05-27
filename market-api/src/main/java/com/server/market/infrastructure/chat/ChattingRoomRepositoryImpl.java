package com.server.market.infrastructure.chat;

import com.server.market.domain.chat.ChattingRoom;
import com.server.market.domain.chat.ChattingRoomRepository;
import com.server.market.domain.chat.dto.ChattingRoomSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ChattingRoomRepositoryImpl implements ChattingRoomRepository {

    private final ChattingRoomJpaRepository chattingRoomJpaRepository;
    private final ChatQueryRepository chatQueryRepository;

    @Override
    public ChattingRoom save(final ChattingRoom chattingRoom) {
        return chattingRoomJpaRepository.save(chattingRoom);
    }

    @Override
    public List<ChattingRoomSimpleResponse> findMyChattingRooms(final Long authId) {
        return chatQueryRepository.findMyChattingRooms(authId);
    }
}
