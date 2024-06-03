package com.server.market.infrastructure.product;

import com.server.market.domain.product.ProductLike;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class ProductLikeJpaRepositoryTest {

    @Autowired
    private ProductLikeJpaRepository productLikeJpaRepository;

    @Test
    void 상품_좋아요를_눌렀다면_true를_반환한다() {
        // given
        ProductLike productLike = ProductLike.builder()
                .productId(1L)
                .memberId(2L)
                .build();

        productLikeJpaRepository.save(productLike);

        // when
        boolean result = productLikeJpaRepository.existsByProductIdAndMemberId(1L, 2L);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 상품_좋아요_저장() {
        // given
        ProductLike productLike = ProductLike.builder()
                .productId(1L)
                .memberId(2L)
                .build();

        // when
        ProductLike save = productLikeJpaRepository.save(productLike);

        // then
        assertThat(save.getId()).isEqualTo(1L);
     }
}
