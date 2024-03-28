package com.server.market.fixture;

import com.server.market.domain.category.Category;
import com.server.market.domain.category.CategoryName;

public class CategoryFixture {

    public static Category 카테고리_생성() {
        return Category.builder()
                .id(1L)
                .name(CategoryName.A001)
                .build();
    }
}
