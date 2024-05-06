package com.server.community.domain.comment;

import com.server.community.domain.comment.dto.CommentSimpleResponse;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findById(Long id);

    List<CommentSimpleResponse> findAllCommentsByBoardId(Long boardId, Long memberId, Long commentId, int pageSize);

    void deleteById(Long id);

    void deleteAllByBoardId(Long boardId);
}
