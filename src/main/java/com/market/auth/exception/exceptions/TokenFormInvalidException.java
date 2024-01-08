package com.market.auth.exception.exceptions;

public class TokenFormInvalidException extends RuntimeException {

    public TokenFormInvalidException() {
        super("토큰의 길이 및 형식이 올바르지 않습니다.");
    }
}
