package com.server.market.domain.product;

import com.server.global.domain.BaseEntity;
import com.server.market.application.product.dto.ProductUpdateResult;
import com.server.market.domain.product.vo.Description;
import com.server.market.domain.product.vo.Location;
import com.server.market.domain.product.vo.Price;
import com.server.market.domain.product.vo.ProductStatus;
import com.server.market.domain.product.vo.StatisticCount;
import com.server.market.exception.exceptions.ProductAlreadySoldOutException;
import com.server.market.exception.exceptions.ProductOwnerNotEqualsException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "product", indexes = {
        @Index(name = "idx_product_finding_specific", columnList = "member_id"),
        @Index(name = "idx_product_paging", columnList = "category_id")
})
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

    @JoinColumn(name = "product_id")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductImage> productImages = new ArrayList<>();

    public static Product of(final String title, final String content, final Location location, final Integer price, final Long categoryId, final Long memberId, final List<MultipartFile> imageFiles, final ProductImageConverter imageConverter) {
        return Product.builder()
                .description(new Description(title, content, location))
                .price(new Price(price))
                .statisticCount(StatisticCount.createDefault())
                .categoryId(categoryId)
                .memberId(memberId)
                .productStatus(ProductStatus.WAITING)
                .productImages(imageConverter.convertImageFilesToImages(imageFiles))
                .build();
    }

    public ProductUpdateResult updateDescription(final String title, final String content, final Location location, final Integer price, final Long categoryId, final Long memberId, final List<MultipartFile> imageFiles, final List<Long> deletedImageIds, final ProductImageConverter imageConverter) {
        validateOwner(memberId);
        this.description.update(title, content, location);
        this.price = new Price(price);
        this.categoryId = categoryId;

        List<ProductImage> addedImages = imageConverter.convertImageFilesToImages(imageFiles);
        List<ProductImage> deletedImages = imageConverter.convertImageIdsToImages(deletedImageIds, this.productImages);

        this.productImages.addAll(addedImages);
        this.productImages.removeAll(deletedImages);

        return new ProductUpdateResult(addedImages, deletedImages);
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

    public void likes(final boolean isNeedToIncrease) {
        this.statisticCount.likes(isNeedToIncrease);
    }
}
