package com.server.market.domain.product;

import com.server.market.domain.product.dto.ProductPagingSimpleResponse;
import com.server.market.domain.product.dto.ProductSpecificResponse;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(Long productId);

    Optional<Product> findByIdWithPessimisticLock(Long productId);

    Optional<ProductSpecificResponse> findSpecificProductById(Long productId, Long memberId);

    void deleteProductById(Long productId);

    List<ProductPagingSimpleResponse> findAllProductsInCategoryWithPaging(Long productId, Long categoryId, int pageSize);
}
