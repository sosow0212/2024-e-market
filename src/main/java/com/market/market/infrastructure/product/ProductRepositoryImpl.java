package com.market.market.infrastructure.product;

import com.market.market.domain.product.Product;
import com.market.market.domain.product.ProductRepository;
import com.market.market.domain.product.dto.ProductPagingSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductQueryRepository productQueryRepository;

    @Override
    public Product save(final Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> findById(final Long productId) {
        return productJpaRepository.findById(productId);
    }

    @Override
    public Optional<Product> findByIdWithPessimisticLock(final Long productId) {
        return productJpaRepository.findByIdWithPessimisticLock(productId);
    }

    @Override
    public void deleteProductById(final Long productId) {
        productJpaRepository.deleteById(productId);
    }

    public List<ProductPagingSimpleResponse> findAllProductsInCategoryWithPaging(final Long productId, final Long categoryId, final int pageSize) {
        return productQueryRepository.findAllWithPagingByCategoryId(productId, categoryId, pageSize);
    }
}
