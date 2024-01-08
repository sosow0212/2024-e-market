package com.market.auth.exception.exceptions;

public class TokenInvalidException extends RuntimeException {

    public TokenInvalidException() {
        super("토큰이 유효하지 않습니다.");
    }
}
