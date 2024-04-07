package batch.server.alarm.application;

import batch.server.alarm.domain.event.ScheduledEvent;
import batch.server.global.event.Events;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Service
public class ScheduleCreateService {

    private final MailService mailService;

    @Scheduled(cron = "0 */20 * * * *")
    public void resendMail() {
        Events.raise(new ScheduledEvent(mailService::resendMail, "resendMail", LocalDateTime.now()));
        log.info("ScheduleService : 재전송 스케쥴링 이벤트 발생");
    }

    @Scheduled(cron = "0 */30 * * * *")
    public void deleteSendSuccessMails() {
        Events.raise(new ScheduledEvent(mailService::deleteSuccessMails, "deleteSendSuccessMail", LocalDateTime.now()));
        log.info("ScheduleService : 이미 보낸 메일 제거 이벤트 발생");
    }
}
