package com.server.community.infrastructure.comment;

import com.server.community.domain.comment.Comment;
import com.server.community.domain.comment.CommentRepository;
import com.server.community.domain.comment.dto.CommentSimpleResponse;

import java.time.LocalDateTime;
import java.util.Comparator;
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
    public List<CommentSimpleResponse> findAllCommentsByBoardId(final Long boardId, final Long memberId, final Long commentId, final int pageSize) {
        if (commentId == null) {
            return map.values().stream()
                    .sorted(Comparator.comparing(Comment::getId).reversed())
                    .limit(pageSize)
                    .map(CommentFakeRepository::parse)
                    .toList();
        }

        return map.values().stream()
                .filter(it -> it.getId() < commentId)
                .filter(it -> it.getBoardId().equals(boardId))
                .sorted(Comparator.comparing(Comment::getId).reversed())
                .limit(pageSize)
                .map(CommentFakeRepository::parse)
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

    private static CommentSimpleResponse parse(final Comment comment) {
        return new CommentSimpleResponse(
                comment.getId(),
                comment.getContent(),
                comment.getWriterId(),
                true,
                "writer",
                LocalDateTime.now()
        );
    }
}
