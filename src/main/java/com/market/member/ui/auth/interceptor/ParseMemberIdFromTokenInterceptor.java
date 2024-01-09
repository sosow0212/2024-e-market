package com.market.member.ui.auth.interceptor;

import com.market.member.ui.auth.support.AuthenticationContext;
import com.market.member.ui.auth.support.AuthenticationExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class ParseMemberIdFromTokenInterceptor implements HandlerInterceptor {

    private final LoginValidCheckerInterceptor loginValidCheckerInterceptor;
    private final AuthenticationContext authenticationContext;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        if (AuthenticationExtractor.extract(request).isEmpty()) {
            authenticationContext.setAnonymous();
            return true;
        }

        return loginValidCheckerInterceptor.preHandle(request, response, handler);
    }
}
