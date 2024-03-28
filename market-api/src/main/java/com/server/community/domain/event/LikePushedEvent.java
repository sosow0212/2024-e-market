package com.server.community.domain.event;

import com.server.global.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikePushedEvent extends Event {

    private final Long boardId;
    private final boolean isIncreaseLike;
}
