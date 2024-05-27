package com.server.market.ui.product.dto;

import com.server.market.domain.product.Product;

public record ProductSimpleResponse(
        Long productId,
        String title,
        Integer price
) {

    public static ProductSimpleResponse from(final Product product) {
        return new ProductSimpleResponse(
                product.getId(),
                product.getDescription().getTitle(),
                product.getPrice().getPrice()
        );
    }
}
