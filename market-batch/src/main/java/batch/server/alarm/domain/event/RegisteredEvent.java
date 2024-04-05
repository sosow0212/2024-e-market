package batch.server.alarm.domain.event;

import java.io.Serializable;

public record RegisteredEvent(
        Long memberId,
        String email,
        String nickname,
        Long timestamp
) implements Serializable {
}

