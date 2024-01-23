package com.market.market.fixture;

import com.market.market.domain.category.Category;
import com.market.market.domain.category.CategoryName;

public class CategoryFixture {

    public static Category 카테고리_생성() {
        return Category.builder()
                .id(1L)
                .name(CategoryName.A001)
                .build();
    }
}
