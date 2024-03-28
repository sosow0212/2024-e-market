package com.server.market.domain.product.dto;

import com.server.market.domain.category.CategoryName;
import com.server.market.domain.product.vo.ProductStatus;

import java.time.LocalDateTime;

public record ProductSpecificResponse(
        Long id,
        String title,
        String content,
        Integer price,
        ProductStatus productStatus,
        Integer visitedCount,
        Integer contactCount,
        Long categoryId,
        CategoryName categoryName,
        String ownerNickname,
        LocalDateTime createDate
) {
}
