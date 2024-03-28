package batch.server.alarm.application;

import batch.server.alarm.domain.MailStorage;
import batch.server.alarm.domain.event.RegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailEventHandler {

    private final MailService mailService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendMail(final RegisteredEvent event) {
        MailStorage mailStorage = MailStorage.createDefault(event.getMemberId(), event.getEmail(), event.getNickname());
        mailService.sendMail(mailStorage);
    }
}
