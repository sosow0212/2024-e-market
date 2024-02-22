package com.market.member.exception.exceptions.member;

public class MemberAuthInvalidException extends RuntimeException {

    public MemberAuthInvalidException() {
        super("권한 정보가 일치하지 않습니다. 요청을 보낸 유저의 정보를 확인해주세요.");
    }
}
