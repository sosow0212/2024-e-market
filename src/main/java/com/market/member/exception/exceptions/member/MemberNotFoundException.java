package com.market.member.exception.exceptions.member;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super("Member를 찾을 수 없습니다.");
    }
}
