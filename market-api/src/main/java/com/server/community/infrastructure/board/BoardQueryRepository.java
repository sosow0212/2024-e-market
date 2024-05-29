package com.server.community.infrastructure.board;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.community.application.board.dto.BoardFoundResponse;
import com.server.community.application.board.dto.BoardSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.querydsl.core.types.ExpressionUtils.count;
import static com.querydsl.core.types.Projections.constructor;
import static com.server.community.domain.board.QBoard.board;
import static com.server.community.domain.board.QLikeStorage.likeStorage;
import static com.server.community.domain.comment.QComment.comment;
import static com.server.member.domain.member.QMember.member;

@RequiredArgsConstructor
@Repository
public class BoardQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<BoardSimpleResponse> findAllBoard(final Pageable pageable, final Long memberId) {
        QueryResults<BoardSimpleResponse> result = jpaQueryFactory.select(
                        constructor(BoardSimpleResponse.class,
                                board.id,
                                member.nickname,
                                board.post.title,
                                board.createdAt,
                                board.likeCount.likeCount,
                                count(comment.id),
                                isLikedAlreadyByMe(memberId)
                        )).from(board)
                .leftJoin(member).on(board.writerId.eq(member.id))
                .leftJoin(comment).on(comment.boardId.eq(board.id))
                .leftJoin(likeStorage).on(likeStorage.boardId.eq(board.id).and(likeStorage.memberId.eq(memberId)))
                .groupBy(board.id, member.nickname, board.post.title, board.createdAt, board.likeCount.likeCount)
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
                                        isLikedAlreadyByMe(memberId),
                                        board.createdAt
                                )
                        ).from(board)
                        .leftJoin(member).on(board.writerId.eq(member.id))
                        .leftJoin(likeStorage).on(likeStorage.boardId.eq(board.id).and(likeStorage.memberId.eq(memberId)))
                        .where(board.id.eq(boardId))
                        .fetchOne()
        );
    }

    private BooleanExpression isLikedAlreadyByMe(final Long memberId) {
        return new CaseBuilder()
                .when(likeStorage.memberId.eq(memberId)).then(true)
                .otherwise(false);
    }

    private BooleanExpression isMyPost(final Long memberId) {
        return new CaseBuilder()
                .when(board.writerId.eq(memberId)).then(true)
                .otherwise(false);
    }
}
