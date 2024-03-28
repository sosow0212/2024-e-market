package batch.server.alarm.fxiture;

import batch.server.alarm.domain.MailStatus;
import batch.server.alarm.domain.MailStorage;

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
