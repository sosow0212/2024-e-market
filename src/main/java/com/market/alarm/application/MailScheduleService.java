package com.market.alarm.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailScheduleService {

    private final MailService mailService;

    @Scheduled(cron = "0 */10 * * * *")
    public void resendMail() {
        mailService.resendMail();
    }

    @Scheduled(cron = "0 */30 * * * *")
    public void deleteSendSuccessMails() {
        mailService.deleteSuccessMails();
    }
}
