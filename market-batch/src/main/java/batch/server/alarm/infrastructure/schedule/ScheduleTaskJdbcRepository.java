package batch.server.alarm.infrastructure.schedule;

import batch.server.alarm.domain.schedule.ScheduleTask;
import batch.server.alarm.domain.schedule.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ScheduleTaskJdbcRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void save(final ScheduleTask scheduleTask) {
        String sql = "INSERT INTO schedule_task (task_id, execution_time, status) VALUES (:taskId, :executionTime, :status)";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("taskId", scheduleTask.getTaskId())
                .addValue("executionTime", scheduleTask.getExecutionTime())
                .addValue("status", scheduleTask.getStatus().toString());

        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public Optional<ScheduleTask> findById(final String taskId) {
        String sql = "SELECT * FROM schedule_task WHERE task_id = :taskId";
        MapSqlParameterSource parameters = new MapSqlParameterSource("taskId", taskId);

        RowMapper<ScheduleTask> rowMapper = (rs, rowNum) -> {
            ScheduleTask scheduleTask = ScheduleTask.builder()
                    .taskId(rs.getString("task_id"))
                    .executionTime(rs.getTimestamp("execution_time").toLocalDateTime())
                    .status(TaskStatus.valueOf(rs.getString("status")))
                    .build();
            return scheduleTask;
        };

        List<ScheduleTask> result = namedParameterJdbcTemplate.query(sql, parameters, rowMapper);

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public void updateTaskStatusById(final String taskId, final TaskStatus taskStatus) {
        String sql = "UPDATE schedule_task SET status = :status WHERE task_id = :taskId";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("status", taskStatus.toString())
                .addValue("taskId", taskId);

        namedParameterJdbcTemplate.update(sql, parameters);
    }


    public void deleteByTaskId(final String taskId) {
        String sql = "DELETE FROM schedule_task WHERE task_id = :taskId";
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("taskId", taskId));
    }
}
