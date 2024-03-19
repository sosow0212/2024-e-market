package com.market.market.fixture;

import com.market.market.domain.product.Product;
import com.market.market.domain.product.dto.ProductPagingSimpleResponse;
import com.market.market.domain.product.vo.Description;
import com.market.market.domain.product.vo.Price;
import com.market.market.domain.product.vo.ProductStatus;
import com.market.market.domain.product.vo.StatisticCount;

import java.time.LocalDateTime;

public class ProductFixture {

    public static Product 상품_생성() {
        return Product.builder()
                .id(1L)
                .categoryId(1L)
                .memberId(1L)
                .description(new Description("title", "content"))
                .statisticCount(StatisticCount.createDefault())
                .price(new Price(10000))
                .productStatus(ProductStatus.WAITING)
                .build();
    }

    public static Product 구매된_상품_생성() {
        return Product.builder()
                .id(1L)
                .categoryId(1L)
                .memberId(1L)
                .description(new Description("title", "content"))
                .statisticCount(StatisticCount.createDefault())
                .price(new Price(10000))
                .productStatus(ProductStatus.COMPLETED)
                .build();
    }

    public static ProductPagingSimpleResponse 상품_페이징_생성() {
        return new ProductPagingSimpleResponse(1L, "상품명", 10000, 10, 2, ProductStatus.WAITING, "귀여운_피그미123", LocalDateTime.now());
    }
}
