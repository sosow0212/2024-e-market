package com.market.alarm.application;

import com.market.alarm.domain.MailSender;
import com.market.alarm.domain.MailStorage;
import com.market.alarm.domain.MailStorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendMail(final MailStorage mailStorage) {
        send(mailStorage);
    }

    private void send(final MailStorage mailStorage) {
        log.info("{} 번 유저 생성. 닉네임 : {}, 메일 발송 시도!", mailStorage.getReceiverId(), mailStorage.getReceiverNickname());

        try {
            mailSender.pushMail(mailStorage.getReceiverEmail(), mailStorage.getId(), mailStorage.getReceiverNickname());
            mailStorage.updateStatusDone();
            log.info("{} 번 유저 생성. 닉네임 : {}, 메일 발송 성공!", mailStorage.getReceiverId(), mailStorage.getReceiverNickname());
        } catch (final Exception exception) {
            handleErrors(mailStorage, exception);
        }
    }

    private void handleErrors(final MailStorage mailStorage, final Exception exception) {
        log.info("{} 번 유저 생성. 닉네임 : {}, 메일 발송 실패!", mailStorage.getReceiverId(), mailStorage.getReceiverNickname());
        log.error(exception.getMessage());

        mailStorage.updateStatusFail();
        mailStorageRepository.save(mailStorage);
    }

    @Transactional
    public void resendMail() {
        List<MailStorage> sendFailureMails = mailStorageRepository.findAllByNotDone();

        if (isRunning.compareAndSet(false, true)) {
            ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

            for (MailStorage sendFailureMail : sendFailureMails) {
                executorService.submit(() -> send(sendFailureMail));
            }

            executorService.shutdown();
            isRunning.set(false);
            log.info("실패 메일 재전송 성공! 건수: {}", sendFailureMails.size());
        }
    }

    @Transactional
    public void deleteSuccessMails() {
        mailStorageRepository.deleteAllByDoneMails();
    }
}
