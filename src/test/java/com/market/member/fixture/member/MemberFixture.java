package com.market.member.fixture.member;

import com.market.member.domain.member.Member;
import com.market.member.domain.member.MemberRole;

public class MemberFixture {

    public static Member 일반_유저_생성() {
        return Member.builder()
                .email("email@email.com")
                .password("password")
                .nickname("nickname")
                .memberRole(MemberRole.MEMBER)
                .build();
    }

    public static Member 일반_유저_생성2() {
        return Member.builder()
                .email("email2@email.com")
                .password("password")
                .nickname("nickname2")
                .memberRole(MemberRole.MEMBER)
                .build();
    }

    public static Member 어드민_유저_생성() {
        return Member.builder()
                .email("email@email.com")
                .password("password")
                .nickname("nickname")
                .memberRole(MemberRole.ADMIN)
                .build();
    }
}
