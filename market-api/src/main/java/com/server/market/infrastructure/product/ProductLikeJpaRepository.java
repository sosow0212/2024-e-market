package com.server.market.infrastructure.product;

import com.server.market.domain.product.ProductLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLikeJpaRepository extends JpaRepository<ProductLike, Long> {

    boolean existsByProductIdAndMemberId(Long productId, Long memberId);

    void deleteByProductIdAndMemberId(Long productId, Long memberId);
}
