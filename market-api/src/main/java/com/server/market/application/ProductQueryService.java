package com.server.market.application;

import com.server.market.domain.product.ProductRepository;
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

    public List<ProductPagingSimpleResponse> findAllProductsInCategory(final Long productId, final Long categoryId, final int pageSize) {
        return productRepository.findAllProductsInCategoryWithPaging(productId, categoryId, pageSize);
    }

    public ProductSpecificResponse findById(final Long productId) {
        return productRepository.findSpecificProductById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }
}