package com.server.market.domain.product;

import com.server.market.domain.product.dto.ProductImageResponse;
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

    List<ProductPagingSimpleResponse> findAllProductsInCategoryWithPaging(Long memberId, Long productId, Long categoryId, int pageSize);

    boolean existsByProductIdAndMemberId(Long productId, Long memberId);

    void deleteByProductIdAndMemberId(Long productId, Long memberId);

    ProductLike saveProductLike(ProductLike productLike);

    List<ProductPagingSimpleResponse> findLikesProducts(Long memberId);

    List<ProductImageResponse> findImages(Long productId);
}
