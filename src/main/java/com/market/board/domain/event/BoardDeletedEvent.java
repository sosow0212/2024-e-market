package com.market.board.domain.event;

import com.market.global.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardDeletedEvent extends Event {

    private final Long boardId;
}
