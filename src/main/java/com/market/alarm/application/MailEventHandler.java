package com.market.alarm.application;

import com.market.alarm.domain.MailSender;
import com.market.member.domain.auth.RegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailEventHandler {

    private final MailSender mailSender;

    @Async
    @EventListener(RegisteredEvent.class)
    public void sendMail(final RegisteredEvent event) {
        log.info("[" + event.getMemberId() + "번 유저 생성. 닉네임 : " + event.getNickname() + "] : 회원가입 축하 메일 발송 완료");

        try {
            mailSender.pushMail(event.getEmail(), event.getMemberId(), event.getNickname());
        } catch (final Exception exception) {
            handleErrors(event, exception);
        }
    }

    private void handleErrors(final RegisteredEvent event, final Exception exception) {
        log.error("이메일 전송 실패 member : " + event.getMemberId() + " " + event.getEmail());
        log.error(exception.getMessage());

        // TODO : 에러 핸들링 (메일 재전송)
    }
}
