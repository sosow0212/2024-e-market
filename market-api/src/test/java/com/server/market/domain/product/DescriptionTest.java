package com.server.market.domain.product;

import com.server.market.domain.product.vo.Description;
import com.server.market.domain.product.vo.Location;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class DescriptionTest {

    @Test
    void 제품_설명을_변경한다() {
        // given
        String newDescription = "new";
        Description description = new Description("title", "content", Location.BUILDING_CENTER);

        // when
        description.update(newDescription, newDescription, Location.BUILDING_CENTER);

        // then
        assertSoftly(softly -> {
            softly.assertThat(description.getTitle()).isEqualTo(newDescription);
            softly.assertThat(description.getContent()).isEqualTo(newDescription);
        });
    }
}
