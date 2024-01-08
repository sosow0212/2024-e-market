package com.market.auth.exception.exceptions;

public class ExpiredTokenException extends RuntimeException {

    public ExpiredTokenException() {
        super("이미 만료된 토큰입니다");
    }
}
