package com.server.member.domain.auth.event;

import com.server.global.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class RegisteredEvent extends Event implements Serializable {

    private final Long memberId;
    private final String email;
    private final String nickname;
}
