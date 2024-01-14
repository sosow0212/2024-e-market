package com.market.alarm.fxiture;

import com.market.alarm.domain.MailStatus;
import com.market.alarm.domain.MailStorage;

public class MailStorageFixture {

    public static MailStorage 이메일_저장소_생성(
            final Long memberId,
            final String email,
            final String nickname,
            final MailStatus mailStatus
    ) {
        return MailStorage.createByStatus(memberId, email, nickname, mailStatus);
    }
}
