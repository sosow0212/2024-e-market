package com.market.market.fixture;

import com.market.market.domain.product.Description;
import com.market.market.domain.product.Product;
import com.market.market.domain.product.StatisticCount;
import com.market.market.domain.product.vo.Price;

public class ProductFixture {

    public static Product 상품_생성() {
        return Product.builder()
                .id(1L)
                .categoryId(1L)
                .memberId(1L)
                .description(new Description("title", "content"))
                .statisticCount(StatisticCount.createDefault())
                .price(new Price(10000))
                .build();
    }
}
