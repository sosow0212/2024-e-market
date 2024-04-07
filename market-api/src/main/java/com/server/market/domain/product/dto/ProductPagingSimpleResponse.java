package com.server.market.domain.product.dto;

import com.server.market.domain.product.vo.ProductStatus;

import java.time.LocalDateTime;

public record ProductPagingSimpleResponse(
        Long id,
        String title,
        Integer price,
        Integer visitedCount,
        Integer contactCount,
        ProductStatus productStatus,
        String ownerName,
        LocalDateTime createDate
) {
}
