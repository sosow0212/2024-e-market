package com.server.member.infrastructure.member;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.member.domain.member.QMember;
import com.server.member.domain.member.dto.TradeHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Function;

import static com.querydsl.core.types.Projections.constructor;
import static com.server.market.domain.product.QProduct.product;
import static com.server.member.domain.member.QTradeHistory.tradeHistory;

@RequiredArgsConstructor
@Repository
public class TradeHistoryQueryRepository {

    private static final String BUYER = "buyer";
    private static final String SELLER = "seller";

    private final JPAQueryFactory jpaQueryFactory;

    public List<TradeHistoryResponse> findTradeHistories(final Long memberId, final boolean isSeller) {
        if (isSeller) {
            return findHistories(memberId, tradeHistory.sellerId::eq);
        }

        return findHistories(memberId, tradeHistory.buyerId::eq);
    }

    private List<TradeHistoryResponse> findHistories(final Long memberId, final Function<Long, BooleanExpression> memberPredicate) {
        QMember buyer = new QMember(BUYER);
        QMember seller = new QMember(SELLER);

        return jpaQueryFactory.select(
                        constructor(TradeHistoryResponse.class,
                                tradeHistory.id,
                                buyer.nickname,
                                seller.nickname,
                                product.id,
                                product.description.title,
                                tradeHistory.productOriginPrice,
                                tradeHistory.productDiscountPrice,
                                tradeHistory.usingCouponIds
                        )
                ).from(tradeHistory)
                .leftJoin(buyer).on(tradeHistory.buyerId.eq(buyer.id))
                .leftJoin(seller).on(tradeHistory.sellerId.eq(seller.id))
                .leftJoin(product).on(tradeHistory.productId.eq(product.id))
                .where(memberPredicate.apply(memberId))
                .fetch();
    }
}
