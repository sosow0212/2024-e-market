package batch.server.alarm.application;

import batch.server.alarm.domain.MailSender;
import batch.server.alarm.domain.MailStorage;
import batch.server.alarm.domain.MailStorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {

    private static final int THREAD_COUNT = 4;

    private final MailStorageRepository mailStorageRepository;
    private final MailSender mailSender;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    @Transactional
    public void sendMail(final MailStorage mailStorage) {
        try {
            mailSender.pushMail(mailStorage.getReceiverEmail(), mailStorage.getId(), mailStorage.getReceiverNickname());
            mailStorage.updateStatusDone();
            log.info("{} 번 유저 생성. 닉네임 : {}, 메일 발송 성공!", mailStorage.getReceiverId(), mailStorage.getReceiverNickname());
        } catch (final Exception exception) {
            saveFailureSendMail(mailStorage, exception);
        }
    }

    private void saveFailureSendMail(final MailStorage mailStorage, final Exception exception) {
        log.error("{} 번 유저 닉네임 : {} 메일 발송 실패! 사유 : {}", mailStorage.getReceiverId(), mailStorage.getReceiverNickname(), exception.getCause());
        mailStorage.updateStatusFail();
    }

    @Transactional
    public void resendMail() {
        List<MailStorage> sendFailureMails = mailStorageRepository.findAllByNotDone();

        if (isRunning.compareAndSet(false, true)) {
            resendFailureMailUsingExecutors(sendFailureMails);
        }
    }

    private void resendFailureMailUsingExecutors(final List<MailStorage> sendFailureMails) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        sendFailureMails.forEach(failureMail -> executorService.submit(() -> sendMail(failureMail)));
        executorService.shutdown();
        isRunning.set(false);

        log.info("실패 메일 재전송 성공! 건수: {}", sendFailureMails.size());
    }

    @Transactional
    public void deleteSuccessMails() {
        mailStorageRepository.deleteAllByDoneMails();
    }
}
