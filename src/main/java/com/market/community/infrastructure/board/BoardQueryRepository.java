package com.market.community.infrastructure.board;

import com.market.community.application.board.dto.BoardSimpleResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.market.community.domain.board.QBoard.board;
import static com.market.member.domain.member.QMember.member;
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
}
