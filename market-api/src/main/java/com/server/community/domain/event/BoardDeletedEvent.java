package com.server.community.domain.event;

import com.server.global.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardDeletedEvent extends Event {

    private final Long boardId;
}
