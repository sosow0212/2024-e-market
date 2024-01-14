package com.market.alarm.infrastructure;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.UnsupportedEncodingException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailSenderTest {

    private EmailSender emailSender;

    @Mock
    private JavaMailSender javaMailSender;

    @BeforeEach
    void setup() {
        emailSender = new EmailSender(javaMailSender);
    }

    @Test
    void 메일을_전송한다() throws MessagingException, UnsupportedEncodingException {
        // given
        String receiver = "to";
        Long id = 1L;
        String nickname = "nickname";
        MimeMessage message = mock(MimeMessage.class);

        when(javaMailSender.createMimeMessage()).thenReturn(message);

        // when
        emailSender.pushMail(receiver, id, nickname);

        // then
        verify(javaMailSender).send(message);
    }
}
