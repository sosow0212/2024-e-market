package com.market.alarm.application;

import com.market.helper.IntegrationHelper;
import com.market.member.domain.auth.RegisteredEvent;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.mockito.Mockito.verify;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MailEventHandlerTest extends IntegrationHelper {

    @Autowired
    private ApplicationContext applicationContext;

    @MockBean
    private MailEventHandler mailEventHandler;

    @Test
    void 회원가입_이벤트가_발행되면_구독자가_동작한다() {
        // given
        RegisteredEvent event = new RegisteredEvent(1L, "email@email.com", "nickname");

        // when
        applicationContext.publishEvent(event);

        // then
        verify(mailEventHandler).sendMail(event);
    }
}
