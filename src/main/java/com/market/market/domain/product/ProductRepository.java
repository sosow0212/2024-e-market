package com.market.market.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(final Product product);

    Optional<Product> findById(final Long productId);

    void deleteProductById(final Long productId);

    List<Product> findAllProductsInCategory(final Long categoryId);
}
