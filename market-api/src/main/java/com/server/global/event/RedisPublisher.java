package com.server.global.event;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisPublisher {

    private static RedisTemplate redisTemplate;

    public static void setTemplate(final RedisTemplate redisTemplate) {
        RedisPublisher.redisTemplate = redisTemplate;
    }

    public static void raise(final String channel, final Object message) {
        if (redisTemplate != null) {
            redisTemplate.convertAndSend(channel, message);
        }
    }
}
