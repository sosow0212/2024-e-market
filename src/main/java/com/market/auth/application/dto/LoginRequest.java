package com.market.auth.application.dto;

public record LoginRequest(
        String email,
        String password
) {
}
