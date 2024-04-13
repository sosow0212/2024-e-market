package com.server.market.fixture;

import com.server.market.domain.category.CategoryName;
import com.server.market.domain.product.Product;
import com.server.market.domain.product.dto.ProductPagingSimpleResponse;
import com.server.market.domain.product.dto.ProductSpecificResponse;
import com.server.market.domain.product.vo.Description;
import com.server.market.domain.product.vo.Location;
import com.server.market.domain.product.vo.Price;
import com.server.market.domain.product.vo.ProductStatus;
import com.server.market.domain.product.vo.StatisticCount;

import java.time.LocalDateTime;

public class ProductFixture {

    public static Product 상품_생성() {
        return Product.builder()
                .id(1L)
                .categoryId(1L)
                .memberId(1L)
                .description(new Description("title", "content", Location.BUILDING_CENTER))
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
                .description(new Description("title", "content", Location.BUILDING_CENTER))
                .statisticCount(StatisticCount.createDefault())
                .price(new Price(10000))
                .productStatus(ProductStatus.COMPLETED)
                .build();
    }

    public static ProductPagingSimpleResponse 상품_페이징_생성() {
        return new ProductPagingSimpleResponse(1L, Location.BUILDING_CENTER, "상품명", 10000, 10, 2, ProductStatus.WAITING, "귀여운_피그미123", LocalDateTime.now());
    }

    public static ProductSpecificResponse 상품_상세정보_생성() {
        return new ProductSpecificResponse(1L, Location.BUILDING_CENTER, "상품명", "상품 참 좋아요~", 10000, ProductStatus.WAITING, 4, 1, 1L, CategoryName.A000, "귀여운_피그미123", LocalDateTime.now());
    }
}
