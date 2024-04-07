package batch.server.alarm.infrastructure.schedule;

import batch.server.alarm.domain.schedule.ScheduleTask;
import batch.server.alarm.domain.schedule.TaskStatus;
import batch.server.helper.IntegrationHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static batch.server.alarm.fxiture.ScheduleTaskFixture.스케줄_생성_진행중;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ScheduleTaskJdbcRepositoryTest extends IntegrationHelper {

    @Autowired
    private ScheduleTaskJdbcRepository scheduleTaskJdbcRepository;

    @Test
    void 태스크를_저장한다() {
        // when & then
        Assertions.assertDoesNotThrow(() -> scheduleTaskJdbcRepository.save(스케줄_생성_진행중()));
    }

    @Test
    void 태스크를_id로_찾는다() {
        // given
        ScheduleTask task = 스케줄_생성_진행중();
        scheduleTaskJdbcRepository.save(task);

        // when
        Optional<ScheduleTask> result = scheduleTaskJdbcRepository.findById(task.getTaskId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).isPresent();
            softly.assertThat(result.get().getTaskId()).isEqualTo(task.getTaskId());
        });
    }

    @Test
    void 태스크_상태를_변경한다() {
        // given
        ScheduleTask task = 스케줄_생성_진행중();
        scheduleTaskJdbcRepository.save(task);
        TaskStatus errorStatus = TaskStatus.ERROR;

        // when
        scheduleTaskJdbcRepository.updateTaskStatusById(task.getTaskId(), errorStatus);

        // then
        Optional<ScheduleTask> result = scheduleTaskJdbcRepository.findById(task.getTaskId());
        assertSoftly(softly -> {
            softly.assertThat(result).isPresent();
            softly.assertThat(result.get().getStatus()).isEqualTo(errorStatus);
        });
    }

    @Test
    void 태스크를_제거한다() {
        // given
        ScheduleTask task = 스케줄_생성_진행중();
        scheduleTaskJdbcRepository.save(task);

        // when
        scheduleTaskJdbcRepository.deleteByTaskId(task.getTaskId());

        // then
        Optional<ScheduleTask> result = scheduleTaskJdbcRepository.findById(task.getTaskId());
        assertThat(result).isEmpty();
    }
}
