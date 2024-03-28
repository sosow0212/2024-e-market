package com.server.community.infrastructure.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CommentJdbcRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void deleteAllCommentsByBoardId(final Long boardId) {
        String sql = "DELETE FROM comment WHERE board_id = :boardId";

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("boardId", boardId));
    }
}

