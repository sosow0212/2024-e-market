package batch.server.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    private static final int THREAD_POLL_SIZE = 10;
    private static final String SCHEDULE_TASK_PREFIX = "schedule-task-";

    @Override
    public void configureTasks(final ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler taskScheduler = getThreadPoolTaskScheduler();
        taskRegistrar.setTaskScheduler(taskScheduler);
    }

    private ThreadPoolTaskScheduler getThreadPoolTaskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(THREAD_POLL_SIZE);
        taskScheduler.setThreadNamePrefix(SCHEDULE_TASK_PREFIX);
        taskScheduler.initialize();
        return taskScheduler;
    }
}
