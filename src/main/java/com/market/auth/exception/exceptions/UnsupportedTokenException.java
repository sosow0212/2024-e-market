package com.market.auth.exception.exceptions;

public class UnsupportedTokenException extends RuntimeException {

    public UnsupportedTokenException() {
        super("지원하지 않는 토큰입니다.");
    }
}
