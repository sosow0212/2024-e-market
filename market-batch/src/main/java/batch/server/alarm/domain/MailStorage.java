package batch.server.alarm.domain;

import batch.server.global.domain.BaseEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MailStorage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Receiver receiver;

    @Enumerated(value = EnumType.STRING)
    private MailStatus mailStatus;

    public static MailStorage createDefault(final Long memberId, final String email, final String nickname) {
        return getMailStorage(memberId, email, nickname, MailStatus.WAIT);
    }

    public static MailStorage createByStatus(final Long memberId, final String email, final String nickname, final MailStatus mailStatus) {
        return getMailStorage(memberId, email, nickname, mailStatus);
    }

    private static MailStorage getMailStorage(final Long memberId, final String email, final String nickname, final MailStatus mailStatus) {
        return MailStorage.builder()
                .receiver(Receiver.createDefault(memberId, email, nickname))
                .mailStatus(mailStatus)
                .build();
    }

    public void updateStatusFail() {
        this.mailStatus = MailStatus.FAIL;
    }

    public void updateStatusDone() {
        this.mailStatus = MailStatus.DONE;
    }

    public Long getReceiverId() {
        return receiver.getMemberId();
    }

    public String getReceiverEmail() {
        return receiver.getEmail();
    }

    public String getReceiverNickname() {
        return receiver.getNickname();
    }
}
