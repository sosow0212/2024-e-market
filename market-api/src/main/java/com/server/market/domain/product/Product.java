package com.server.market.domain.product;

import com.server.global.domain.BaseEntity;
import com.server.market.domain.product.vo.Description;
import com.server.market.domain.product.vo.Location;
import com.server.market.domain.product.vo.Price;
import com.server.market.domain.product.vo.ProductStatus;
import com.server.market.domain.product.vo.StatisticCount;
import com.server.market.exception.exceptions.ProductAlreadySoldOutException;
import com.server.market.exception.exceptions.ProductOwnerNotEqualsException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Description description;

    @Embedded
    private Price price;

    @Embedded
    private StatisticCount statisticCount;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProductStatus productStatus;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false)
    private Long memberId;

    public static Product of(final String title, final String content, final Location location, final Integer price, final Long categoryId, final Long memberId) {
        return Product.builder()
                .description(new Description(title, content, location))
                .price(new Price(price))
                .statisticCount(StatisticCount.createDefault())
                .categoryId(categoryId)
                .memberId(memberId)
                .productStatus(ProductStatus.WAITING)
                .build();
    }

    public void updateDescription(final String title, final String content, final Location location, final Integer price, final Long categoryId, final Long memberId) {
        validateOwner(memberId);
        this.description.update(title, content, location);
        this.price = new Price(price);
        this.categoryId = categoryId;
    }

    public void validateOwner(final Long memberId) {
        if (!this.memberId.equals(memberId)) {
            throw new ProductOwnerNotEqualsException();
        }
    }

    public void view(final boolean canAddViewCount) {
        this.statisticCount.view(canAddViewCount);
    }

    public void sell() {
        if (this.productStatus.isCompleted()) {
            throw new ProductAlreadySoldOutException();
        }

        this.productStatus = ProductStatus.COMPLETED;
    }
}
