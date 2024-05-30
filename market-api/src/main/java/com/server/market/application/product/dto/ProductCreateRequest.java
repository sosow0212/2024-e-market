package com.server.market.application.product.dto;

import com.server.market.domain.product.vo.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public record ProductCreateRequest(
        @NotBlank(message = "상품명을 입력해주세요")
        String title,

        @NotBlank(message = "상품 설명을 입력해주세요")
        String content,

        @NotNull(message = "상품 가격을 입력해주세요")
        Integer price,

        @NotNull(message = "거래 장소를 입력해주세요")
        Location location,

        List<MultipartFile> images
) {

    public ProductCreateRequest(final String title, final String content, final Integer price, final Location location, final List<MultipartFile> images) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.location = location;
        this.images = handleAddedImages(images);
    }

    private List<MultipartFile> handleAddedImages(final List<MultipartFile> addedImages) {
        if (addedImages == null) {
            return new ArrayList<>();
        }

        return addedImages;
    }
}
