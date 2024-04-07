package batch.server.global.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(name = "schedule.enable", havingValue = "true")
@EnableScheduling
@Component
public class ScheduleEnableConfig {
}
