package com.market.community.infrastructure.comment;

import com.market.community.domain.comment.Comment;
import com.market.community.domain.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;
    private final CommentJdbcRepository commentJdbcRepository;

    @Override
    public Comment save(final Comment comment) {
        return commentJpaRepository.save(comment);
    }

    @Override
    public Optional<Comment> findById(final Long id) {
        return commentJpaRepository.findById(id);
    }

    @Override
    public List<Comment> findAllCommentsByBoardId(final Long boardId) {
        return commentJpaRepository.findAll();
    }

    @Override
    public void deleteById(final Long id) {
        commentJpaRepository.deleteById(id);
    }

    @Override
    public void deleteAllByBoardId(final Long boardId) {
        commentJdbcRepository.deleteAllCommentsByBoardId(boardId);
    }
}
