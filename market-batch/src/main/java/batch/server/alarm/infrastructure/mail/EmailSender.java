package batch.server.alarm.infrastructure.mail;

import batch.server.alarm.config.MailSource;
import batch.server.alarm.domain.mail.MailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

import static jakarta.mail.Message.RecipientType.TO;

@Slf4j
@RequiredArgsConstructor
@Component
public class EmailSender implements MailSender {

    private static final String CHARSET = "utf-8";
    private static final String SUBTYPE = "html";

    @Value("${mail.sender.email}")
    private String email;

    @Value("${mail.sender.name}")
    private String name;

    private final JavaMailSender javaMailSender;

    @Override
    public void pushMail(
            final String receiver,
            final Long id,
            final String nickname
    ) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = createMessage(receiver, id, nickname);
        javaMailSender.send(message);
    }


    private MimeMessage createMessage(
            final String receiver,
            final Long id,
            final String nickname
    ) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(TO, receiver);
        message.setSubject(MailSource.TITLE.getMessage());
        message.setText(MailSource.getMailMessage(id, nickname), CHARSET, SUBTYPE);
        message.setFrom(new InternetAddress(email, name));

        return message;
    }
}
