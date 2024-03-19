package com.market.market.application;

import com.market.market.domain.product.ProductRepository;
import com.market.market.domain.product.dto.ProductPagingSimpleResponse;
import com.market.market.domain.product.dto.ProductSpecificResponse;
import com.market.market.exception.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductQueryService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductPagingSimpleResponse> findAllProductsInCategory(final Long productId, final Long categoryId, final int pageSize) {
        return productRepository.findAllProductsInCategoryWithPaging(productId, categoryId, pageSize);
    }

    public ProductSpecificResponse findById(final Long productId) {
        return productRepository.findSpecificProductById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }
}
