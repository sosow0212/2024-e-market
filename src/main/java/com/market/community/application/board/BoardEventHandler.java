package com.market.community.application.board;

import com.market.community.domain.event.LikePushedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardEventHandler {

    private final BoardService boardService;

    @EventListener
    public void patchLike(final LikePushedEvent event) {
        boardService.patchLike(event.getBoardId(), event.isIncreaseLike());
    }
}
