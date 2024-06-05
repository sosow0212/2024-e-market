package com.server.member.domain.member.dto;

import com.server.market.domain.product.vo.Location;
import com.server.market.domain.product.vo.ProductStatus;

import java.time.LocalDateTime;

public record ProductByMemberResponse(
        Long productId,
        Long sellerId,
        String title,
        Integer price,
        String location,
        ProductStatus productStatus,
        LocalDateTime createTime
) {

    public ProductByMemberResponse(final Long productId, final Long sellerId, final String title, final Integer price, final Location location, final ProductStatus productStatus, final LocalDateTime createTime) {
        this(productId, sellerId, title, price, location.getContent(), productStatus, createTime);
    }
}
