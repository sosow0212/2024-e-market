package com.market.member.infrastructure.member;

import com.market.member.domain.member.NicknameGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NicknameGeneratorImpl implements NicknameGenerator {

    private static final String SEPARATOR = "-";
    private static final String BLANK = "";
    private static final int LENGTH_OF_UUID = 8;

    @Override
    public String createRandomNickname() {
        return NicknamePrefix.getPrefix() +
                NicknameCandidate.getCandidate() +
                "_" +
                generateUUID();

    }

    private String generateUUID() {
        return UUID.randomUUID().toString()
                .replaceAll(SEPARATOR, BLANK)
                .substring(0, LENGTH_OF_UUID);
    }
}
