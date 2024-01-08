package com.market.auth.application.dto;

public record SignupRequest(
         String nickname,
         String email,
         String password
) {
}
