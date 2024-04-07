package com.server.community.infrastructure.board;

import com.server.community.application.board.dto.BoardFoundResponse;
import com.server.community.application.board.dto.BoardSimpleResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.server.community.domain.board.QBoard.board;
import static com.server.member.domain.member.QMember.member;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
@Repository
public class BoardQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<BoardSimpleResponse> findAllBoard(Pageable pageable) {
        QueryResults<BoardSimpleResponse> result = jpaQueryFactory.select(
                        constructor(BoardSimpleResponse.class,
                                board.id,
                                member.nickname,
                                board.post.title,
                                board.createdAt
                        )).from(board)
                .leftJoin(member).on(board.writerId.eq(member.id))
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    public Optional<BoardFoundResponse> findById(final Long boardId, final Long memberId) {
        return Optional.ofNullable(
                jpaQueryFactory.select(
                                constructor(BoardFoundResponse.class,
                                        board.id,
                                        member.nickname,
                                        board.post.title,
                                        board.post.content,
                                        board.likeCount.likeCount,
                                        isMyPost(memberId),
                                        board.createdAt
                                )
                        ).from(board)
                        .where(board.id.eq(boardId))
                        .leftJoin(member).on(board.writerId.eq(member.id))
                        .fetchOne()
        );
    }

    private BooleanExpression isMyPost(final Long memberId) {
        return new CaseBuilder()
                .when(board.writerId.eq(memberId)).then(true)
                .otherwise(false);
    }
}
