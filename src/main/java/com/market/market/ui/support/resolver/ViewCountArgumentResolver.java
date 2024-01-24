package com.market.market.ui.support.resolver;

import com.market.market.ui.support.ViewCountChecker;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class ViewCountArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String COOKIE_NAME = "productView";
    private static final int COOKIE_VALID_TIME = 60 * 60 * 24;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ViewCountChecker.class) &&
                parameter.getParameterType().equals(Boolean.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory) throws Exception {
        boolean canAddViewCount = false;

        HttpServletResponse response = (HttpServletResponse) webRequest.getNativeResponse();
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String requestURI = request.getRequestURI();
        String productId = requestURI.substring(requestURI.lastIndexOf("/") + 1);

        Cookie productViewCookie = findCookie(request);

        if (productViewCookie != null && !hasProductIdInCookie(productViewCookie, productId)) {
            addProductIdInCookie(productViewCookie, productId, response);
            canAddViewCount = true;
        }

        if (productViewCookie == null) {
            createProductIdCookie(productId, response);
            canAddViewCount = true;
        }

        return canAddViewCount;
    }

    private Cookie findCookie(final HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(it -> it.getName().equals(COOKIE_NAME))
                    .findAny()
                    .orElseGet(null);
        }

        return null;
    }

    private boolean hasProductIdInCookie(final Cookie cookie, final String productId) {
        return cookie.getValue().contains("[" + productId + "]");
    }

    private void addProductIdInCookie(final Cookie productViewCookie, final String productId, final HttpServletResponse response) {
        productViewCookie.setValue(productViewCookie.getValue() + "_[" + productId + "]");
        addCookie(productViewCookie, response);
    }

    private void createProductIdCookie(final String productId, final HttpServletResponse response) {
        Cookie newCookie = new Cookie(COOKIE_NAME, "[" + productId + "]");
        addCookie(newCookie, response);
    }

    private void addCookie(final Cookie cookie, final HttpServletResponse response) {
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_VALID_TIME);
        response.addCookie(cookie);
    }
}
