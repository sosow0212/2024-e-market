package com.server.market.infrastructure.product;

import com.server.market.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.server.market.fixture.ProductFixture.상품_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class ProductJpaRepositoryTest {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    private Product product;

    @BeforeEach
    void setup() {
        product = 상품_생성();
    }

    @Test
    void 상품을_저장한다() {
        // when
        Product saved = productJpaRepository.save(product);

        // then
        assertThat(saved).usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(product);
    }

    @Test
    void 상품을_id로_찾는다() {
        // given
        Product saved = productJpaRepository.save(product);

        // when
        Optional<Product> found = productJpaRepository.findById(saved.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(found).isPresent();
            softly.assertThat(found.get())
                    .usingRecursiveComparison()
                    .isEqualTo(saved);
        });
    }

    @Test
    void 상품을_id로_제거한다() {
        // given
        Product saved = productJpaRepository.save(product);

        // when
        productJpaRepository.deleteById(saved.getId());

        // then
        Optional<Product> found = productJpaRepository.findById(saved.getId());
        assertThat(found).isEmpty();
    }

    @Test
    void 카테고리id에_해당되는_상품들을_모두_찾는다() {
        // given
        Product saved = productJpaRepository.save(product);

        // when
        List<Product> found = productJpaRepository.findAllByCategoryId(saved.getCategoryId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(found).hasSize(1);
            softly.assertThat(found.get(0))
                    .usingRecursiveComparison()
                    .isEqualTo(saved);
        });
    }
}
