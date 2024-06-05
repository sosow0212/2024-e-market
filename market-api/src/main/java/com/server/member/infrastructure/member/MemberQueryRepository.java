package com.server.member.infrastructure.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.member.domain.member.dto.ProductByMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.types.Projections.constructor;
import static com.server.market.domain.product.QProduct.product;

@RequiredArgsConstructor
@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ProductByMemberResponse> findProductsByMemberId(final Long memberId) {
        return jpaQueryFactory.select(constructor(ProductByMemberResponse.class,
                        product.id,
                        product.memberId,
                        product.description.title,
                        product.price,
                        product.description.location,
                        product.productStatus,
                        product.createdAt
                )).from(product)
                .where(product.memberId.eq(memberId))
                .fetch();
    }
}
