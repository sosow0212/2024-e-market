package com.market.community.application.comment;

import com.market.community.domain.event.BoardDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentEventHandler {

    private final CommentService commentService;

    @EventListener
    public void deleteAllCommentsByBoardId(final BoardDeletedEvent event) {
        Long deletedBoardId = event.getBoardId();

        commentService.deleteAllCommentsByBoardId(deletedBoardId);
        log.info("{} 번 게시글에 포함된 댓글 모두 삭제 완료!", deletedBoardId);
    }
}
