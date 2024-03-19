package com.market.market.application;

import com.market.market.domain.product.Product;
import com.market.market.domain.product.ProductRepository;
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
    public List<Product> findAllProductsInCategory(final Long categoryId) {
        return productRepository.findAllProductsInCategory(categoryId);
    }
}
