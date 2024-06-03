package com.server.market.domain.category;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CategoryNameTest {

    @Test
    void 카테고리id에_해당하는_카테고리_네임_반환을한다() {
        // given
        Long categoryId = 1L;

        // when
        CategoryName result = CategoryName.from(categoryId);

        // then
        assertThat(result).isEqualTo(CategoryName.A001);
    }
}
