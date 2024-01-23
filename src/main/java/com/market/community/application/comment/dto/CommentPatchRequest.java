package com.market.community.application.comment.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentPatchRequest(

        @NotBlank(message = "내용이 공백입니다.")
        String comment
) {
}
