package com.market.comment.infrastructure;

import com.market.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

    Comment save(final Comment comment);

    Optional<Comment> findById(final Long id);

    void deleteById(final Long id);
}
