package batch.server.alarm.application;

import batch.server.alarm.domain.event.RegisteredEvent;
import batch.server.global.event.Events;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthMailMessageListener implements MessageListener {

    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(final Message message, final byte[] pattern) {
        try {
            RegisteredEvent registeredEvent = objectMapper.readValue(message.getBody(), RegisteredEvent.class);
            log.info("Subscriber :: auth-mail 메시지 수신 성공, " + registeredEvent.email());
            Events.raise(registeredEvent);
        } catch (IOException e) {
            log.error("Subscriber :: auth-mail 메시지 수신 실패, " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
