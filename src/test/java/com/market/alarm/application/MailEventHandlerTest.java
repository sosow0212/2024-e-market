package com.market.alarm.application;

import com.market.helper.IntegrationHelper;
import com.market.member.application.auth.AuthService;
import com.market.member.application.auth.dto.SignupRequest;
import com.market.member.domain.auth.event.RegisteredEvent;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MailEventHandlerTest extends IntegrationHelper {

    @MockBean
    private MailEventHandler mailEventHandler;

    @Autowired
    private AuthService authService;

    @Test
    void 회원가입_이벤트가_발행되면_구독자가_동작한다() {
        // given
        doNothing().when(mailEventHandler).sendMail(any());

        // when
        authService.signup(new SignupRequest("email@email.com", "1234"));

        // then
        verify(mailEventHandler).sendMail(any(RegisteredEvent.class));
    }
}
