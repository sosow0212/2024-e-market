package com.market.market.infrastructure.product;

import com.market.market.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    Product save(final Product product);

    Optional<Product> findById(final Long productId);

    void deleteById(final Long productId);

    List<Product> findAllByCategoryId(final Long categoryId);
}
