package batch.server.alarm.domain.schedule;

import java.util.Optional;

public interface ScheduleTaskRepository {

    void save(ScheduleTask scheduleTask);

    Optional<ScheduleTask> findById(String jobId);

    void updateTaskStatusById(String taskId, TaskStatus taskStatus);

    void deleteByTaskId(String taskId);
}
