package com.server.market.infrastructure.product;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.market.domain.product.QProduct;
import com.server.market.domain.product.QProductImage;
import com.server.market.domain.product.QProductLike;
import com.server.market.domain.product.dto.ProductImageResponse;
import com.server.market.domain.product.dto.ProductPagingSimpleResponse;
import com.server.market.domain.product.dto.ProductSpecificResponse;
import com.server.member.domain.member.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.core.types.dsl.Expressions.stringTemplate;
import static com.server.market.domain.category.QCategory.category;
import static com.server.market.domain.product.QProduct.product;
import static com.server.market.domain.product.QProductImage.productImage;
import static com.server.market.domain.product.QProductLike.productLike;
import static com.server.member.domain.member.QMember.member;

@RequiredArgsConstructor
@Repository
public class ProductQueryRepository {

    private static final long NOT_FOUND_IMAGE_NUMBER = -1L;

    private final JPAQueryFactory jpaQueryFactory;

    public List<ProductPagingSimpleResponse> findAllWithPagingByCategoryId(
            final Long memberId,
            final Long productId,
            final Long categoryId,
            final int pageSize
    ) {
        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;
        QMember member = QMember.member;
        QProductLike productLike = QProductLike.productLike;

        return jpaQueryFactory.select(Projections.constructor(ProductPagingSimpleResponse.class,
                        product.id,
                        new CaseBuilder()
                                .when(productImage.id.isNull())
                                .then(NOT_FOUND_IMAGE_NUMBER)
                                .otherwise(productImage.id).as("imageId"),
                        new CaseBuilder()
                                .when(productImage.uniqueName.isNull())
                                .then("null")
                                .otherwise(
                                        stringTemplate("CONCAT('https://atwozimage.s3.ap-northeast-2.amazonaws.com/', {0})", productImage.uniqueName)
                                ).as("uniqueName"),
                        product.description.location,
                        product.description.title,
                        product.price.price,
                        product.statisticCount.visitedCount,
                        product.statisticCount.contactCount,
                        product.productStatus,
                        member.id,
                        member.nickname,
                        product.statisticCount.likedCount,
                        isLikedAlreadyByMe(memberId),
                        product.createdAt
                ))
                .from(product)
                .leftJoin(member).on(product.memberId.eq(member.id))
                .leftJoin(productLike).on(productLike.productId.eq(product.id).and(productLike.memberId.eq(memberId)))
                .leftJoin(productImage).on(productImage.product.id.eq(product.id))
                .where(
                        ltProductId(productId),
                        categoryId != null ? product.categoryId.eq(categoryId) : product.categoryId.isNull()
                )
                .orderBy(product.id.desc())
                .limit(pageSize)
                .fetch();
    }

    private BooleanExpression isLikedAlreadyByMe(final Long memberId) {
        return new CaseBuilder()
                .when(productLike.memberId.eq(memberId)).then(true)
                .otherwise(false);
    }

    private BooleanExpression ltProductId(final Long productId) {
        if (productId == null) {
            return null;
        }

        return product.id.lt(productId);
    }

    public List<ProductImageResponse> findImages(final Long productId) {
        return jpaQueryFactory.select(
                        constructor(ProductImageResponse.class,
                                productImage.id,
                                productImage.uniqueName
                        )
                ).from(productImage)
                .where(productImage.product.id.eq(productId))
                .fetch();
    }


    public Optional<ProductSpecificResponse> findSpecificProductById(final Long id, final Long memberId) {
        return Optional.ofNullable(jpaQueryFactory.select(constructor(ProductSpecificResponse.class,
                        product.id,
                        product.description.location,
                        product.description.title,
                        product.description.content,
                        product.price.price,
                        product.productStatus,
                        product.statisticCount.visitedCount,
                        product.statisticCount.contactCount,
                        product.categoryId,
                        member.id,
                        member.nickname,
                        member.id.eq(memberId),
                        product.statisticCount.likedCount,
                        isLikedAlreadyByMe(memberId),
                        product.createdAt
                )).from(product)
                .where(product.id.eq(id))
                .leftJoin(member).on(member.id.eq(product.memberId))
                .leftJoin(productLike).on(productLike.productId.eq(product.id).and(productLike.memberId.eq(memberId)))
                .leftJoin(category).on(category.id.eq(product.categoryId))
                .fetchOne());
    }

    public List<ProductPagingSimpleResponse> findLikesProducts(final Long memberId) {
        return jpaQueryFactory.select(constructor(ProductPagingSimpleResponse.class,
                        product.id,
                        product.description.location,
                        product.description.title,
                        product.price.price,
                        product.statisticCount.visitedCount,
                        product.statisticCount.contactCount,
                        product.productStatus,
                        member.id,
                        member.nickname,
                        product.statisticCount.likedCount,
                        isLikedAlreadyByMe(memberId),
                        product.createdAt
                )).from(product)
                .orderBy(product.id.desc())
                .leftJoin(member).on(product.memberId.eq(member.id))
                .leftJoin(productLike).on(productLike.productId.eq(product.id).and(productLike.memberId.eq(memberId)))
                .where(productLike.memberId.eq(memberId))
                .fetch();
    }
}
