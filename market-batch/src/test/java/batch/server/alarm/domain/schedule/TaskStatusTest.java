package batch.server.alarm.domain.schedule;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class TaskStatusTest {

    @Test
    void 상태가_Done이거나_Running이면_false를_반환한다() {
        // given
        TaskStatus running = TaskStatus.RUNNING;
        TaskStatus done = TaskStatus.DONE;

        // when
        boolean runningResult = running.canWork();
        boolean doneResult = done.canWork();

        // then
        assertSoftly(softly -> {
            softly.assertThat(runningResult).isFalse();
            softly.assertThat(doneResult).isFalse();
        });
    }
}
