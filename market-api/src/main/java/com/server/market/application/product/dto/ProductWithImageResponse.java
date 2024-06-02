package com.server.market.application.product.dto;

import com.server.market.domain.product.dto.ProductImageResponse;
import com.server.market.domain.product.dto.ProductSpecificResponse;

import java.util.List;

public record ProductWithImageResponse(
        ProductSpecificResponse product,
        List<ProductImageResponse> images
) {
}
