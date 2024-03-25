package com.market.community.infrastructure.comment;

import com.market.community.domain.comment.dto.CommentSimpleResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.market.community.domain.comment.QComment.comment;
import static com.market.member.domain.member.QMember.member;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
@Repository
public class CommentQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<CommentSimpleResponse> findAllWithPaging(final Long boardId, final Long commentId, final int pageSize) {
        return jpaQueryFactory.select(constructor(CommentSimpleResponse.class,
                        comment.id,
                        comment.content,
                        member.id,
                        member.nickname,
                        comment.createdAt
                )).from(comment)
                .where(
                        ltCommentId(commentId),
                        comment.boardId.eq(boardId)
                )
                .orderBy(comment.id.desc())
                .leftJoin(member).on(comment.writerId.eq(member.id))
                .limit(pageSize)
                .fetch();
    }

    private BooleanExpression ltCommentId(final Long commentId) {
        if (commentId == null) {
            return null;
        }

        return comment.id.lt(commentId);
    }

}
