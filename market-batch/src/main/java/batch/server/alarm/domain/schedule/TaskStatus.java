package batch.server.alarm.domain.schedule;

public enum TaskStatus {

    WAITING,
    RUNNING,
    DONE,
    ERROR;

    public boolean canWork() {
        return this == TaskStatus.WAITING
                || this == TaskStatus.ERROR;
    }
}
