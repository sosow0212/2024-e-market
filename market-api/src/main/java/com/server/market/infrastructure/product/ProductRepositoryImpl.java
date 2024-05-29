package com.server.market.infrastructure.product;

import com.server.market.domain.product.Product;
import com.server.market.domain.product.ProductLike;
import com.server.market.domain.product.ProductRepository;
import com.server.market.domain.product.dto.ProductPagingSimpleResponse;
import com.server.market.domain.product.dto.ProductSpecificResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductQueryRepository productQueryRepository;
    private final ProductLikeJpaRepository productLikeJpaRepository;

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
    public Optional<ProductSpecificResponse> findSpecificProductById(final Long productId, final Long memberId) {
        return productQueryRepository.findSpecificProductById(productId, memberId);
    }

    @Override
    public void deleteProductById(final Long productId) {
        productJpaRepository.deleteById(productId);
    }

    public List<ProductPagingSimpleResponse> findAllProductsInCategoryWithPaging(final Long memberId, final Long productId, final Long categoryId, final int pageSize) {
        return productQueryRepository.findAllWithPagingByCategoryId(memberId, productId, categoryId, pageSize);
    }

    @Override
    public boolean existsByProductIdAndMemberId(final Long productId, final Long memberId) {
        return productLikeJpaRepository.existsByProductIdAndMemberId(productId, memberId);
    }

    @Override
    public void deleteByProductIdAndMemberId(final Long productId, final Long memberId) {
        productLikeJpaRepository.deleteByProductIdAndMemberId(productId, memberId);
    }

    @Override
    public ProductLike saveProductLike(final ProductLike productLike) {
        return productLikeJpaRepository.save(productLike);
    }

    @Override
    public List<ProductPagingSimpleResponse> findLikesProducts(final Long memberId) {
        return productQueryRepository.findLikesProducts(memberId);
    }
}
