package com.market.member.application.auth.dto;

public record SignupRequest(
         String nickname,
         String email,
         String password
) {
}
