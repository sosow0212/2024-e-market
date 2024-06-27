package batch.server.alarm.application;

import batch.server.alarm.domain.event.ScheduledEvent;
import batch.server.alarm.domain.schedule.ScheduleTask;
import batch.server.alarm.domain.schedule.ScheduleTaskRepository;
import batch.server.alarm.domain.schedule.TaskStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScheduleTaskEventHandler {

    private static final String RUNNING_EVERY_FIVE_SECOND = "0/5 * * * * *";
    private static final int THREAD_COUNT = 1;

    private final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private final Queue<ScheduledEvent> scheduledTasks = new ConcurrentLinkedDeque<>();
    private final ScheduleTaskRepository scheduleTaskRepository;

    @EventListener
    public void addScheduleTask(final ScheduledEvent event) {
        this.scheduledTasks.add(event);
    }

    @Scheduled(cron = RUNNING_EVERY_FIVE_SECOND)
    public void runScheduleTask() {
        if (!canWorkTask()) {
            return;
        }

        executorService.execute(() -> runTask(scheduledTasks.poll()));
    }

    private boolean canWorkTask() {
        return !scheduledTasks.isEmpty()
                || isRunning.compareAndSet(false, true);
    }

    private void runTask(final ScheduledEvent task) {
        String taskId = task.taskId();
        LocalDateTime executionTime = task.date();

        if (doesAlreadyProcess(taskId)) {
            log.info("스케줄링 이미 실행중 :: {}, 시작 시간 :: {}", taskId, executionTime);
            return;
        }

        ScheduleTask scheduleTask = ScheduleTask.createRunningTask(taskId, executionTime);
        scheduleTaskRepository.save(scheduleTask);

        try {
            task.job().run();
            scheduleTaskRepository.deleteByTaskId(task.taskId());
            log.info("스케줄링 작업 성공 :: {}", taskId);
        } catch (Exception e) {
            log.error("스케줄링 작업 실행 중 에러 발생 :: {}", taskId);
            scheduleTask.error();
            scheduleTaskRepository.updateTaskStatusById(scheduleTask.getTaskId(), TaskStatus.ERROR);
            scheduledTasks.add(task);
        }
    }

    private boolean doesAlreadyProcess(String jobId) {
        return scheduleTaskRepository.findById(jobId)
                .map(scheduleTask -> !scheduleTask.canWork())
                .orElse(false);
    }
}
