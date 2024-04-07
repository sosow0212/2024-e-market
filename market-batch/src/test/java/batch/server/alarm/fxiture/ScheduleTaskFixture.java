package batch.server.alarm.fxiture;

import batch.server.alarm.domain.schedule.ScheduleTask;
import batch.server.alarm.domain.schedule.TaskStatus;

import java.time.LocalDateTime;

public class ScheduleTaskFixture {

    public static ScheduleTask 스케줄_생성_진행중() {
        return ScheduleTask.builder()
                .taskId("task")
                .executionTime(LocalDateTime.now())
                .status(TaskStatus.RUNNING)
                .build();
    }
}
