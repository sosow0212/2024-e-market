package com.market.market.application;

import com.market.market.application.dto.ProductCreateRequest;
import com.market.market.application.dto.ProductUpdateRequest;
import com.market.market.domain.product.Product;
import com.market.market.domain.product.ProductRepository;
import com.market.market.exception.exceptions.ProductNotFoundException;
import com.market.market.exception.exceptions.ProductOwnerNotEqualsException;
import com.market.market.infrastructure.product.ProductFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.market.market.fixture.ProductFixture.상품_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository = new ProductFakeRepository();
        productService = new ProductService(productRepository);
    }

    @Test
    void 카테고리에_속한_상품을_모두_찾는다() {
        // given
        Product savedProduct = productRepository.save(상품_생성());

        // when
        List<Product> result = productService.findAllProductsInCategory(savedProduct.getCategoryId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).hasSize(1);
            softly.assertThat(result.get(0))
                    .usingRecursiveComparison()
                    .isEqualTo(savedProduct);
        });
    }

    @Test
    void 상품을_등록한다() {
        // given
        ProductCreateRequest request = new ProductCreateRequest("new", "new", 10);

        // when
        Long id = productService.uploadProduct(1L, 1L, request);

        // then
        assertThat(id).isEqualTo(1L);
    }

    @Test
    void 상품을_id로_조회한다() {
        // given
        Product savedProduct = productRepository.save(상품_생성());

        // when
        Product found = productService.findProductById(savedProduct.getId());

        // then
        assertThat(savedProduct)
                .usingRecursiveComparison()
                .isEqualTo(found);
    }

    @Test
    void 상품이_존재하지_않으면_예외를_발생시킨다() {
        // when & then
        assertThatThrownBy(() -> productService.findProductById(-1L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void 상품을_업데이트한다() {
        // given
        Product savedProduct = productRepository.save(상품_생성());
        ProductUpdateRequest request = new ProductUpdateRequest("new", "new", 1000, 2L);

        // when
        productService.update(savedProduct.getId(), savedProduct.getMemberId(), request);

        // then
        assertSoftly(softly -> {
            softly.assertThat(savedProduct.getDescription().getTitle()).isEqualTo(request.title());
            softly.assertThat(savedProduct.getDescription().getContent()).isEqualTo(request.content());
            softly.assertThat(savedProduct.getPrice().getPrice()).isEqualTo(1000);
            softly.assertThat(savedProduct.getCategoryId()).isEqualTo(2L);
        });
    }

    @Test
    void 상품_업데이트시에_상품이_없다면_예외를_발생시킨다() {
        // given
        Product savedProduct = productRepository.save(상품_생성());
        ProductUpdateRequest request = new ProductUpdateRequest("new", "new", 1000, 2L);

        // when & then
        assertThatThrownBy(() -> productService.update(savedProduct.getId(), -1L, request))
                .isInstanceOf(ProductOwnerNotEqualsException.class);
    }

    @Test
    void 상품_업데이트시에_상품의_주인과_다를시_예외를_발생시킨다() {
        // given
        ProductUpdateRequest request = new ProductUpdateRequest("new", "new", 1000, 2L);

        // when & then
        assertThatThrownBy(() -> productService.update(-1L, -1L, request))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void 상품을_id로_제거한다() {
        // given
        Product savedProduct = productRepository.save(상품_생성());

        // when
        productService.delete(savedProduct.getId(), savedProduct.getMemberId());

        // then
        Optional<Product> found = productRepository.findById(savedProduct.getId());
        assertThat(found).isEmpty();
    }

    @Test
    void 상품제거시에_상품이_존재하지_않으면_예외를_발생시킨다() {
        // when & then
        assertThatThrownBy(() -> productService.delete(-1L, 1L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void 상품_제거시에_상품_주인과_일치하지_않다면_예외를_발생시킨다() {
        // given
        Product savedProduct = productRepository.save(상품_생성());

        // when & then
        assertThatThrownBy(() -> productService.delete(savedProduct.getId(), -1L))
                .isInstanceOf(ProductOwnerNotEqualsException.class);
    }
}

