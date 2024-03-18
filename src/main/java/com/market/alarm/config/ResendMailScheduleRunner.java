package com.market.alarm.config;

import com.market.alarm.application.MailScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@ConditionalOnProperty(name = "schedule.resend-mail", havingValue = "true")
@Configuration
public class ResendMailScheduleRunner implements ApplicationRunner {

    private final MailScheduleService mailScheduleService;

    @Override
    public void run(final ApplicationArguments args) {
        mailScheduleService.resendMail();
    }
}
