package com.server.member.ui.auth.support.resolver;

import com.server.member.exception.exceptions.auth.LoginInvalidException;
import com.server.member.ui.auth.support.AuthMember;
import com.server.member.ui.auth.support.AuthenticationContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private static final int ANONYMOUS = -1;

    private final AuthenticationContext authenticationContext;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMember.class) &&
                parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory) throws Exception {
        Long memberId = authenticationContext.getPrincipal();

        if (memberId == ANONYMOUS) {
            throw new LoginInvalidException();
        }

        return memberId;
    }
}
