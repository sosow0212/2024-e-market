package com.market.market.domain.product;

import com.market.market.domain.product.dto.ProductPagingSimpleResponse;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(final Product product);

    Optional<Product> findById(final Long productId);

    Optional<Product> findByIdWithPessimisticLock(final Long productId);

    void deleteProductById(final Long productId);

    List<ProductPagingSimpleResponse> findAllProductsInCategoryWithPaging(final Long productId, final Long categoryId, final int pageSize);
}
