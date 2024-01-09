package com.market.member.exception.exceptions.member;

public class MemberAlreadyExistedException extends RuntimeException {

    public MemberAlreadyExistedException() {
        super("이미 존재하는 Email입니다.");
    }
}
