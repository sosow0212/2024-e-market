package com.server.market.domain.product.dto;

import com.server.market.domain.category.CategoryName;
import com.server.market.domain.product.vo.Location;
import com.server.market.domain.product.vo.ProductStatus;

import java.time.LocalDateTime;

public record ProductSpecificResponse(
        Long id,
        String location,
        String title,
        String content,
        Integer price,
        ProductStatus productStatus,
        Integer visitedCount,
        Integer contactCount,
        Long categoryId,
        CategoryName categoryName,
        Long ownerId,
        String ownerNickname,
        Boolean isMyProduct,
        Integer likedCount,
        Boolean isLikedAlreadyByMe,
        LocalDateTime createDate
) {

    public ProductSpecificResponse(final Long id, final Location location, final String title, final String content, final Integer price, final ProductStatus productStatus, final Integer visitedCount, final Integer contactCount, final Long categoryId, final CategoryName categoryName, final Long ownerId, final String ownerNickname, final Boolean isMyProduct, final Integer likedCount, final Boolean isLikedAlreadyByMe, final LocalDateTime createDate) {
        this(id, location.getContent(), title, content, price, productStatus, visitedCount, contactCount, categoryId, categoryName, ownerId, ownerNickname, isMyProduct, likedCount, isLikedAlreadyByMe, createDate);
    }
}
