package com.market.member.application.auth.dto;

public record LoginRequest(
        String email,
        String password
) {
}
