package com.market.market.infrastructure.product;

import com.market.market.domain.product.Product;
import com.market.market.domain.product.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductFakeRepository implements ProductRepository {

    private final Map<Long, Product> map = new HashMap<>();
    private Long id = 0L;

    @Override
    public Product save(final Product product) {
        id++;

        Product saved = Product.builder()
                .id(id)
                .description(product.getDescription())
                .statisticCount(product.getStatisticCount())
                .memberId(product.getMemberId())
                .categoryId(product.getCategoryId())
                .price(product.getPrice())
                .productStatus(product.getProductStatus())
                .build();

        map.put(id, saved);
        return saved;
    }

    @Override
    public Optional<Product> findById(final Long productId) {
        return map.keySet().stream()
                .filter(it -> it.equals(productId))
                .map(map::get)
                .findAny();
    }

    @Override
    public Optional<Product> findByIdWithPessimisticLock(final Long productId) {
        return map.keySet().stream()
                .filter(it -> it.equals(productId))
                .map(map::get)
                .findAny();
    }

    @Override
    public void deleteProductById(final Long productId) {
        map.remove(productId);
    }

    @Override
    public List<Product> findAllProductsInCategory(final Long categoryId) {
        return map.values().stream()
                .filter(it -> it.getCategoryId().equals(categoryId))
                .toList();
    }
}
