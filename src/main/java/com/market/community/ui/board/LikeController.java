package com.market.community.ui.board;

import com.market.community.application.board.LikeService;
import com.market.community.application.board.dto.LikeResultResponse;
import com.market.member.ui.auth.support.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/boards")
@RestController
public class LikeController {

    private final LikeService likeService;

    @PatchMapping("/{boardId}/likes")
    public ResponseEntity<LikeResultResponse> patchLike(@AuthMember final Long memberId, @PathVariable("boardId") final Long boardId) {
        boolean likeStatus = likeService.patchLike(boardId, memberId);
        return ResponseEntity.ok(new LikeResultResponse(boardId, likeStatus));
    }
}
