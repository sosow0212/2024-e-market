package com.market.member.exception.exceptions.member;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException() {
        super("권한을 찾을 수 없습니다.");
    }
}
