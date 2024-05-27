package com.server.market.ui.product.dto;

import com.server.market.domain.product.Product;

import java.util.List;
import java.util.stream.Collectors;

public record ProductsResponse(
        List<ProductSimpleResponse> products
) {

    public static ProductsResponse from(final List<Product> products) {
        return products.stream()
                .map(ProductSimpleResponse::from)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ProductsResponse::new));
    }
}
