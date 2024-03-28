package batch.server.alarm.domain;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface MailSender {

    void pushMail(final String receiver,
                  final Long id,
                  final String nickname) throws MessagingException, UnsupportedEncodingException;
}
