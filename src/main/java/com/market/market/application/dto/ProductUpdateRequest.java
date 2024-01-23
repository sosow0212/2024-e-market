package com.market.market.application.dto;

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
        Long categoryId
) {
}
