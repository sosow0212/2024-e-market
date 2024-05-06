package com.server.community.ui.comment;

import com.server.community.application.comment.CommentQueryService;
import com.server.community.application.comment.CommentService;
import com.server.community.application.comment.dto.CommentCreateRequest;
import com.server.community.application.comment.dto.CommentPatchRequest;
import com.server.community.domain.comment.dto.CommentSimpleResponse;
import com.server.member.ui.auth.support.AuthMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/boards/")
@RestController
public class CommentController {

    private final CommentService commentService;
    private final CommentQueryService commentQueryService;

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<Void> createComment(@AuthMember final Long memberId,
                                              @PathVariable("boardId") final Long boardId,
                                              @Valid @RequestBody final CommentCreateRequest request) {
        commentService.createComment(memberId, boardId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/{boardId}/comments")
    public ResponseEntity<List<CommentSimpleResponse>> findAllCommentsByBoardId(@PathVariable("boardId") final Long boardId,
                                                                                @RequestParam(name = "commentId", required = false) final Long commentId,
                                                                                @AuthMember final Long memberId,
                                                                                @RequestParam(name = "pageSize") final Integer pageSize) {
        List<CommentSimpleResponse> comments = commentQueryService.findAllCommentsByBoardId(boardId, memberId, commentId, pageSize);
        return ResponseEntity.ok(comments);
    }

    @PatchMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<Void> patchComment(@PathVariable("boardId") final Long boardId,
                                             @PathVariable("commentId") final Long commentId,
                                             @AuthMember final Long memberId,
                                             @Valid @RequestBody final CommentPatchRequest request) {
        commentService.patchCommentById(memberId, commentId, request);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("boardId") final Long boardId,
                                              @PathVariable("commentId") final Long commentId,
                                              @AuthMember final Long memberId) {
        commentService.deleteCommentById(memberId, commentId);
        return ResponseEntity.noContent()
                .build();
    }
}
