package com.market.market.domain.product;

import com.market.market.exception.exceptions.ProductOwnerNotEqualsException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.market.market.fixture.ProductFixture.상품_생성;
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
        product.updateDescription(newDescription, newDescription, 10, 1L, 1L);

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
        assertThatThrownBy(() -> product.updateDescription(newDescription, newDescription, 10, 1L, -1L))
                .isInstanceOf(ProductOwnerNotEqualsException.class);
    }
}
