package com.server.member.exception.exceptions.member;

public class PasswordNotMatchedException extends RuntimeException {

    public PasswordNotMatchedException() {
        super("패스워드가 일치하지 않습니다.");
    }
}
