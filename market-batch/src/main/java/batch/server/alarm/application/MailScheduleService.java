package batch.server.alarm.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class MailScheduleService {

    private final MailService mailService;

    @Scheduled(cron = "0 */10 * * * *")
    public void resendMail() {
        log.info("재전송 스케쥴링 시작");
        mailService.resendMail();
    }

    @Scheduled(cron = "0 */30 * * * *")
    public void deleteSendSuccessMails() {
        log.info("이미 보낸 메일 제거 스케쥴링 시작");
        mailService.deleteSuccessMails();
    }
}
