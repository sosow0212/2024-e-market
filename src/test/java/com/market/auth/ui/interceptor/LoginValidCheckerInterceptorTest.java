package com.market.auth.ui.interceptor;

import com.market.auth.exception.exceptions.LoginInvalidException;
import com.market.auth.infrastructure.JwtTokenProvider;
import com.market.auth.ui.support.AuthenticationContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class LoginValidCheckerInterceptorTest {

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse res = mock(HttpServletResponse.class);

    @Test
    void token이_없다면_예외를_발생한다() {
        // given
        LoginValidCheckerInterceptor loginValidCheckerInterceptor = new LoginValidCheckerInterceptor(
                new JwtTokenProvider(),
                new AuthenticationContext()
        );

        when(req.getHeader("any")).thenReturn(null);

        // when
        assertThatThrownBy(() -> loginValidCheckerInterceptor.preHandle(req, res, new Object()))
                .isInstanceOf(LoginInvalidException.class);
    }
}
