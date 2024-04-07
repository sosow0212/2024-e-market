package batch.server.alarm.domain.mail.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class Receiver {

    private Long memberId;
    private String email;
    private String nickname;

    public static Receiver createDefault(final Long memberId, final String email, final String nickname) {
        return Receiver.builder()
                .memberId(memberId)
                .email(email)
                .nickname(nickname)
                .build();
    }
}
