package com.server.market.application.product.dto;

import com.server.market.domain.product.vo.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public record ProductUpdateRequest(
        @NotBlank(message = "상품명을 입력해주세요")
        String title,

        @NotBlank(message = "상품 설명을 입력해주세요")
        String content,

        @NotNull(message = "상품 가격을 입력해주세요")
        Integer price,

        @NotNull(message = "카테고리 id를 입력해주세요")
        Long categoryId,

        @NotNull(message = "거래 장소를 입력해주세요")
        Location location,

        List<MultipartFile> addedImages,

        List<Long> deletedImages
) {

    public ProductUpdateRequest(final String title, final String content, final Integer price, final Long categoryId, final Location location, final List<MultipartFile> addedImages, final List<Long> deletedImages) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.categoryId = categoryId;
        this.location = location;
        this.addedImages = handleAddedImages(addedImages);
        this.deletedImages = handleDeletedImages(deletedImages);
    }

    private List<MultipartFile> handleAddedImages(final List<MultipartFile> addedImages) {
        if (addedImages == null) {
            return new ArrayList<>();
        }

        return addedImages;
    }

    private List<Long> handleDeletedImages(final List<Long> deletedImages) {
        if (deletedImages == null) {
            return new ArrayList<>();
        }

        return deletedImages;
    }
}
