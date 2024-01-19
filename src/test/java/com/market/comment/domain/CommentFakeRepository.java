package com.market.comment.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CommentFakeRepository implements CommentRepository {

    private final Map<Long, Comment> map = new HashMap<>();

    private Long id = 0L;

    @Override
    public Comment save(final Comment comment) {
        id++;

        Comment saved = Comment.builder()
                .id(id)
                .content(comment.getContent())
                .writerId(comment.getWriterId())
                .boardId(comment.getBoardId())
                .build();

        map.put(id, saved);
        return saved;
    }

    @Override
    public Optional<Comment> findById(final Long id) {
        return map.values().stream()
                .filter(comment -> comment.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Comment> findAllCommentsByBoardId(final Long boardId) {
        return map.values().stream()
                .filter(it -> it.getBoardId().equals(boardId))
                .toList();
    }

    @Override
    public void deleteById(final Long id) {
        map.remove(id);
        this.id--;
    }

    @Override
    public void deleteAllByBoardId(final Long boardId) {
        List<Long> commentIds = map.values().stream()
                .filter(it -> it.getBoardId().equals(boardId))
                .map(Comment::getId)
                .toList();

        for (Long id : commentIds) {
            map.remove(id);
        }
    }
}
