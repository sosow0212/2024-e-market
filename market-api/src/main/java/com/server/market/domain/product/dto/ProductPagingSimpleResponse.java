package com.server.market.domain.product.dto;

import com.server.market.domain.product.vo.Location;
import com.server.market.domain.product.vo.ProductStatus;

import java.time.LocalDateTime;

public record ProductPagingSimpleResponse(
        Long id,
        String location,
        String title,
        Integer price,
        Integer visitedCount,
        Integer contactCount,
        ProductStatus productStatus,
        Long ownerId,
        String ownerName,
        Integer productLikesCount,
        Boolean isAlreadyLikedByMe,
        LocalDateTime createDate
) {
    public ProductPagingSimpleResponse(
            final Long id,
            final Location location,
            final String title,
            final Integer price,
            final Integer visitedCount,
            final Integer contactCount,
            final ProductStatus productStatus,
            final Long ownerId,
            final String ownerName,
            final Integer productLikesCount,
            final Boolean isAlreadyLikedByMe,
            final LocalDateTime createDate
    ) {
        this(id, location.getContent(), title, price, visitedCount, contactCount, productStatus, ownerId, ownerName, productLikesCount, isAlreadyLikedByMe, createDate);
    }
}
