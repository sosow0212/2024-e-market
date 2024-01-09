package com.market.member.application.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record SignupRequest(
        @NotBlank(message = "닉네임을 입력해주세요.")
        String nickname,

        @NotBlank(message = "이메일을 입력해주세요.")
        String email,

        @NotBlank(message = "패스워드를 입력해주세요.")
        String password
) {
}
