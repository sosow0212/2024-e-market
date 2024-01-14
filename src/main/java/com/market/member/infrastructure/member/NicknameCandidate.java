package com.market.member.infrastructure.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NicknameCandidate {

    CANDIDATE_01("강아지"),
    CANDIDATE_02("고양이"),
    CANDIDATE_03("돼지"),
    CANDIDATE_04("기린"),
    CANDIDATE_05("원숭이");

    private final String candidate;

    public static String getCandidate() {
        int pick = (int) (Math.random() * values().length);
        return values()[pick].candidate;
    }
}
