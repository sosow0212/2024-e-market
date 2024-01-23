package com.market.market.infrastructure.product;

import com.market.market.domain.product.Product;
import com.market.market.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product save(final Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> findById(final Long productId) {
        return productJpaRepository.findById(productId);
    }

    @Override
    public void deleteProductById(final Long productId) {
        productJpaRepository.deleteById(productId);
    }

    @Override
    public List<Product> findAllProductsInCategory(final Long categoryId) {
        return productJpaRepository.findAllByCategoryId(categoryId);
    }
}
