package com.server.market.application.product;

import com.server.market.domain.product.ProductRepository;
import com.server.market.domain.product.dto.ProductPagingSimpleResponse;
import com.server.market.domain.product.dto.ProductSpecificResponse;
import com.server.market.exception.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductQueryService {

    private final ProductRepository productRepository;

    public List<ProductPagingSimpleResponse> findAllProductsInCategory(final Long memberId, final Long productId, final Long categoryId, final int pageSize) {
        return productRepository.findAllProductsInCategoryWithPaging(memberId, productId, categoryId, pageSize);
    }

    public ProductSpecificResponse findById(final Long productId, final Long memberId) {
        return productRepository.findSpecificProductById(productId, memberId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public List<ProductPagingSimpleResponse> findLikesProducts(final Long memberId) {
        return productRepository.findLikesProducts(memberId);
    }
}
