package com.market.alarm.infrastructure;

import com.market.alarm.domain.MailStatus;
import com.market.alarm.domain.MailStorage;
import com.market.helper.JdbcTestHelper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.market.alarm.fxiture.MailStorageFixture.이메일_저장소_생성;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MailStorageJdbcRepositoryTest extends JdbcTestHelper {

    @Autowired
    private MailStorageJpaRepository mailStorageJpaRepository;

    @Autowired
    private MailStorageJdbcRepository mailStorageJdbcRepository;

    @Test
    void 메일_상태에_기반하여_batch_제거한다() {
        // given
        Long memberId = 1L;
        String email = "email@email.com";
        String nickname = "nickname";

        MailStatus done = MailStatus.DONE;
        MailStatus wait = MailStatus.WAIT;

        mailStorageJpaRepository.save(이메일_저장소_생성(memberId, email, nickname, done));
        mailStorageJpaRepository.save(이메일_저장소_생성(2L, email + "m", nickname + "m", wait));

        // when
        mailStorageJdbcRepository.deleteAllByMailStatus(done);

        // then
        List<MailStorage> deleted = mailStorageJpaRepository.findAllByMailStatus(done);
        List<MailStorage> waited = mailStorageJpaRepository.findAllByMailStatus(wait);

        assertSoftly(softly -> {
            softly.assertThat(deleted).isEmpty();
            softly.assertThat(waited).hasSize(1);
        });
    }
}
