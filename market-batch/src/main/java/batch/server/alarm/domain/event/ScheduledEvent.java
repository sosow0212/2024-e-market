package batch.server.alarm.domain.event;

import java.time.LocalDateTime;

public record ScheduledEvent(
        Runnable job,
        String taskId,
        LocalDateTime date
) {
}
