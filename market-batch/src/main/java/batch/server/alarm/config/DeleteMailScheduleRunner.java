package batch.server.alarm.config;

import batch.server.alarm.application.MailScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@ConditionalOnProperty(name = "schedule.delete-mail", havingValue = "true")
@Configuration
public class DeleteMailScheduleRunner implements ApplicationRunner {

    private final MailScheduleService mailScheduleService;

    @Override
    public void run(final ApplicationArguments args) {
        mailScheduleService.deleteSendSuccessMails();
    }
}
