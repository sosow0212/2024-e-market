package com.server.member.infrastructure.member;

import com.server.member.domain.member.QMember;
import com.server.member.domain.member.dto.TradeHistoryResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.server.market.domain.product.QProduct.product;
import static com.server.member.domain.member.QTradeHistory.tradeHistory;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
@Repository
public class TradeHistoryQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<TradeHistoryResponse> findTradeHistories(final Long memberId, final boolean isSeller) {
        if (isSeller) {
            return findSellerHistories(memberId);
        }

        return findBuyerHistories(memberId);
    }

    private List<TradeHistoryResponse> findSellerHistories(final Long sellerId) {
        QMember buyer = new QMember("buyer");
        QMember seller = new QMember("seller");

        return jpaQueryFactory.select(
                        constructor(TradeHistoryResponse.class,
                                tradeHistory.id,
                                buyer.nickname,
                                seller.nickname,
                                product.description.title,
                                tradeHistory.productOriginPrice,
                                tradeHistory.productDiscountPrice,
                                tradeHistory.usingCouponIds
                        )
                ).from(tradeHistory)
                .leftJoin(buyer).on(tradeHistory.buyerId.eq(buyer.id))
                .leftJoin(seller).on(tradeHistory.sellerId.eq(seller.id))
                .leftJoin(product).on(tradeHistory.productId.eq(product.id))
                .where(tradeHistory.sellerId.eq(sellerId))
                .fetch();
    }

    private List<TradeHistoryResponse> findBuyerHistories(final Long buyerId) {
        QMember buyer = new QMember("buyer");
        QMember seller = new QMember("seller");

        return jpaQueryFactory.select(
                        constructor(TradeHistoryResponse.class,
                                tradeHistory.id,
                                buyer.nickname,
                                seller.nickname,
                                product.description.title,
                                tradeHistory.productOriginPrice,
                                tradeHistory.productDiscountPrice,
                                tradeHistory.usingCouponIds
                        )
                ).from(tradeHistory)
                .leftJoin(buyer).on(tradeHistory.buyerId.eq(buyer.id))
                .leftJoin(seller).on(tradeHistory.sellerId.eq(seller.id))
                .leftJoin(product).on(tradeHistory.productId.eq(product.id))
                .where(tradeHistory.buyerId.eq(buyerId))
                .fetch();
    }
}
