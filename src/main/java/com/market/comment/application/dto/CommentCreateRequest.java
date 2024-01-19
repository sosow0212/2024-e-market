package com.market.comment.application.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentCreateRequest(

        @NotBlank(message = "댓글은 공백이 될 수 없습니다.")
        String comment
) {
}
