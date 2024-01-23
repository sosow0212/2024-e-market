package com.market.market.infrastructure;

import com.market.market.domain.category.Category;
import com.market.market.infrastructure.category.CategoryJpaRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.market.market.fixture.CategoryFixture.카테고리_생성;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class CategoryJpaRepositoryTest {

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @Test
    void 카테고리를_저장한다() {
        // given
        Category category = 카테고리_생성();

        // when
        Category saved = categoryJpaRepository.save(category);

        // then
        assertThat(saved.getId()).isEqualTo(1L);
    }
}
