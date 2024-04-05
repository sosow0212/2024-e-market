package com.server.member.application.auth;

import com.server.global.event.RedisPublisher;
import com.server.member.domain.auth.event.RegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthEventListener {

    private static final String PUBLISH_CHANNEL = "auth-mail";

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishMail(final RegisteredEvent event) {
        RedisPublisher.raise(PUBLISH_CHANNEL, new RegisteredEvent(event.getMemberId(), event.getEmail(), event.getNickname()));
        log.info("Publisher :: " + PUBLISH_CHANNEL + "채널 " + event.getEmail() + "발행 성공!");
    }
}
