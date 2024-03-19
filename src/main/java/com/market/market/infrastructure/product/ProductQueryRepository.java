package com.market.market.infrastructure.product;

import com.market.market.domain.product.dto.ProductPagingSimpleResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.market.market.domain.product.QProduct.product;
import static com.market.member.domain.member.QMember.member;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
@Repository
public class ProductQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ProductPagingSimpleResponse> findAllWithPagingByCategoryId(final Long productId, final Long categoryId, final int pageSize) {
        return jpaQueryFactory.select(constructor(ProductPagingSimpleResponse.class,
                        product.id,
                        product.description.title,
                        product.price.price,
                        product.statisticCount.visitedCount,
                        product.statisticCount.contactCount,
                        product.productStatus,
                        member.nickname,
                        product.createdAt
                )).from(product)
                .where(
                        ltProductId(productId),
                        product.categoryId.eq(categoryId)
                ).orderBy(product.id.desc())
                .leftJoin(member).on(product.memberId.eq(member.id))
                .limit(pageSize)
                .fetch();
    }

    private BooleanExpression ltProductId(final Long productId) {
        if (productId == null) {
            return null;
        }

        return product.id.lt(productId);
    }
}
