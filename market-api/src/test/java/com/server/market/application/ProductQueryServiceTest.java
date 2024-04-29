package com.server.market.application;

import com.server.helper.IntegrationHelper;
import com.server.market.domain.category.Category;
import com.server.market.domain.category.CategoryRepository;
import com.server.market.domain.product.Product;
import com.server.market.domain.product.ProductRepository;
import com.server.market.domain.product.dto.ProductPagingSimpleResponse;
import com.server.market.domain.product.dto.ProductSpecificResponse;
import com.server.member.domain.member.Member;
import com.server.member.domain.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.server.market.fixture.CategoryFixture.카테고리_생성;
import static com.server.market.fixture.ProductFixture.상품_생성;
import static com.server.member.fixture.member.MemberFixture.일반_유저_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ProductQueryServiceTest extends IntegrationHelper {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductQueryService productQueryService;

    private Member member;
    private Product product;
    private Category category;

    @BeforeEach
    void setup() {
        member = memberRepository.save(일반_유저_생성());
        category = categoryRepository.save(카테고리_생성());
        product = productRepository.save(상품_생성());
    }

    @Test
    void 상품을_페이징_조회한다() {
        // when
        List<ProductPagingSimpleResponse> result = productQueryService.findAllProductsInCategory(null, category.getId(), 10);

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).hasSize(1);
            softly.assertThat(result.get(0).id()).isEqualTo(product.getId());
        });
    }

    @Test
    void 상품_상세_조회() {
        // when
        ProductSpecificResponse result = productQueryService.findById(product.getId(), 1L);

        // then
        assertThat(result.id()).isEqualTo(product.getId());
    }
}
