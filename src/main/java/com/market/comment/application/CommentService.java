package com.market.comment.application;

import com.market.comment.application.dto.CommentCreateRequest;
import com.market.comment.application.dto.CommentPatchRequest;
import com.market.comment.domain.Comment;
import com.market.comment.domain.CommentRepository;
import com.market.comment.exception.exceptions.CommentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public void createComment(final Long memberId, final Long boardId, final CommentCreateRequest request) {
        Comment comment = Comment.builder()
                .boardId(boardId)
                .writerId(memberId)
                .content(request.comment())
                .build();

        commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> findAllCommentsByBoardId(final Long boardId) {
        return commentRepository.findAllCommentsByBoardId(boardId);
    }

    @Transactional
    public void patchCommentById(final Long memberId, final Long commentId, final CommentPatchRequest request) {
        Comment comment = findComment(commentId);
        comment.update(request.comment(), memberId);
    }

    private Comment findComment(final Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }

    @Transactional
    public void deleteCommentById(final Long memberId, final Long commentId) {
        Comment comment = findComment(commentId);
        comment.validateWriterId(memberId);

        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void deleteAllCommentsByBoardId(final Long deletedBoardId) {
        commentRepository.deleteAllByBoardId(deletedBoardId);
    }
}
