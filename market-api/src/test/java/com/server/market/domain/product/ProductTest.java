package com.server.market.domain.product;

import com.server.market.application.product.dto.ProductUpdateResult;
import com.server.market.domain.product.vo.Location;
import com.server.market.exception.exceptions.ProductAlreadySoldOutException;
import com.server.market.exception.exceptions.ProductOwnerNotEqualsException;
import com.server.market.infrastructure.product.ProductImageConverterImpl;
import com.server.market.infrastructure.product.ProductImageFakeConverter;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.server.market.fixture.ProductFixture.구매된_상품_생성;
import static com.server.market.fixture.ProductFixture.상품_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ProductTest {

    @Test
    void 상품의_설명을_변경한다() {
        // given
        String newDescription = "new";
        Product product = 상품_생성();

        // when
        ProductUpdateResult result = product.updateDescription(newDescription, newDescription, Location.BUILDING_CENTER, 10, 1L, 1L, new ArrayList<>(), new ArrayList<>(), new ProductImageConverterImpl());

        // then
        assertSoftly(softly -> {
            softly.assertThat(product.getPrice().getPrice()).isEqualTo(10);
            softly.assertThat(product.getDescription().getTitle()).isEqualTo(newDescription);
            softly.assertThat(product.getDescription().getContent()).isEqualTo(newDescription);
        });
    }

    @Test
    void 상품의_주인이_다르면_업데이트시에_예외를_발생한다() {
        // given
        String newDescription = "new";
        Product product = 상품_생성();

        // when & then
        assertThatThrownBy(() -> product.updateDescription(newDescription, newDescription, Location.BUILDING_CENTER, 10, 1L, -1L, new ArrayList<>(), new ArrayList<>(), new ProductImageFakeConverter()))
                .isInstanceOf(ProductOwnerNotEqualsException.class);
    }

    @Test
    void 상품_조회수를_올린다() {
        // given
        Product product = 상품_생성();
        Integer beforeViewCount = product.getStatisticCount().getVisitedCount();

        // when
        product.view(true);

        // then
        assertThat(product.getStatisticCount().getVisitedCount())
                .isEqualTo(beforeViewCount + 1);
    }

    @Test
    void 구매된_상품이면_주문하지_못한다() {
        // given
        Product product = 구매된_상품_생성();

        // when & then
        assertThatThrownBy(product::sell)
                .isInstanceOf(ProductAlreadySoldOutException.class);
    }
}
