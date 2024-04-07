package com.server.member.ui.auth.interceptor;

import com.server.member.domain.auth.TokenProvider;
import com.server.member.exception.exceptions.auth.LoginInvalidException;
import com.server.member.ui.auth.support.AuthenticationContext;
import com.server.member.ui.auth.support.AuthenticationExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class LoginValidCheckerInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;
    private final AuthenticationContext authenticationContext;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        String token = AuthenticationExtractor.extract(request)
                .orElseThrow(LoginInvalidException::new);

        Long memberId = tokenProvider.extract(token);
        authenticationContext.setAuthentication(memberId);

        return true;
    }
}
