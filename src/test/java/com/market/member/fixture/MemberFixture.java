package com.market.member.fixture;

import com.market.member.domain.Member;
import com.market.member.domain.MemberRole;

public class MemberFixture {

    public static Member 일반_유저_생성() {
        return Member.builder()
                .nickname("nickname")
                .email("email@email.com")
                .password("password")
                .memberRole(MemberRole.MEMBER)
                .build();
    }
}
