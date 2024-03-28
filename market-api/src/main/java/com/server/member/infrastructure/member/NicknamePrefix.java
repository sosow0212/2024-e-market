package com.server.member.infrastructure.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NicknamePrefix {

    PREFIX_01("현명한"),
    PREFIX_02("귀여운"),
    PREFIX_03("검정색의"),
    PREFIX_04("핑크색의"),
    PREFIX_05("매력적인");

    private final String prefix;

    public static String getPrefix() {
        int pick = (int) (Math.random() * values().length);
        return values()[pick].prefix;
    }
}
