package com.server.market.application.product;

import com.server.market.application.product.dto.ProductWithImageResponse;
import com.server.market.domain.product.ProductRepository;
import com.server.market.domain.product.dto.ProductImageResponse;
import com.server.market.domain.product.dto.ProductPagingSimpleResponse;
import com.server.market.domain.product.dto.ProductSpecificResponse;
import com.server.market.exception.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductQueryService {

    private final ProductRepository productRepository;

    public List<ProductPagingSimpleResponse> findAllProductsInCategory(
            final Long memberId,
            final Long productId,
            final Long categoryId,
            final int pageSize
    ) {
        return productRepository.findAllProductsInCategoryWithPaging(memberId, productId, categoryId, pageSize);
    }

    public ProductWithImageResponse findById(final Long productId, final Long memberId) {
        List<ProductImageResponse> images = productRepository.findImages(productId);
        ProductSpecificResponse product = productRepository.findSpecificProductById(productId, memberId)
                .orElseThrow(ProductNotFoundException::new);
        return new ProductWithImageResponse(product, images);
    }

    public List<ProductPagingSimpleResponse> findLikesProducts(final Long memberId) {
        return productRepository.findLikesProducts(memberId);
    }
}
