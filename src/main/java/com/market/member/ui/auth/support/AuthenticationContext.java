package com.market.member.ui.auth.support;

import com.market.member.exception.exceptions.auth.LoginInvalidException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Objects;

@RequestScope
@Component
public class AuthenticationContext {

    private static final Long ANONYMOUS_MEMBER = -1L;

    private Long memberId;

    public void setAuthentication(Long memberId) {
        this.memberId = memberId;
    }

    public Long getPrincipal() {
        if (Objects.isNull(this.memberId)) {
            throw new LoginInvalidException();
        }

        return memberId;
    }

    public void setAnonymous() {
        this.memberId = ANONYMOUS_MEMBER;
    }
}
