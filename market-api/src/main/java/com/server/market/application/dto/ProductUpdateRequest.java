package com.server.market.application.dto;

import com.server.market.domain.product.vo.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
        Location location
) {
}
