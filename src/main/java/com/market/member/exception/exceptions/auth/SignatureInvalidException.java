package com.market.member.exception.exceptions.auth;

public class SignatureInvalidException extends RuntimeException {

    public SignatureInvalidException() {
        super("서명을 확인하지 못했습니다.");
    }
}
