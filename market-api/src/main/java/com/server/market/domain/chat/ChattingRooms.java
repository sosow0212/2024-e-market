package com.server.market.domain.chat;

import lombok.Getter;

import java.util.List;

@Getter
public class ChattingRooms {

    private final List<ChattingRoom> chattingRooms;

    public ChattingRooms(final List<ChattingRoom> chattingRooms) {
        this.chattingRooms = chattingRooms;
    }

    public void done() {
        this.chattingRooms.forEach(ChattingRoom::done);
    }
}
