package com.market.comment.domain;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(final Comment comment);

    Optional<Comment> findById(final Long id);

    List<Comment> findAllCommentsByBoardId(final Long boardId);

    void deleteById(final Long id);

    void deleteAllByBoardId(final Long boardId);
}
