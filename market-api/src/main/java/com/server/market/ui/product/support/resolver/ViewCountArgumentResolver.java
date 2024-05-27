package com.server.market.ui.product.support.resolver;

import com.server.market.ui.product.support.ViewCountChecker;
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

@RequiredArgsConstructor
@Component
public class ViewCountArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String REQUEST_URL_SEPARATOR = "/";

    private final ProductCookieHelper productCookieHelper;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ViewCountChecker.class) &&
                parameter.getParameterType().equals(Boolean.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory) {
        HttpServletResponse response = (HttpServletResponse) webRequest.getNativeResponse();
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String requestURI = request.getRequestURI();
        String productId = requestURI.substring(requestURI.lastIndexOf(REQUEST_URL_SEPARATOR) + 1);

        Cookie cookie = productCookieHelper.findCookie(request);

        if (productCookieHelper.hasAlreadyVisitedProduct(cookie, productId)) {
            return false;
        }

        productCookieHelper.updateCookie(response, cookie, productId);
        return true;
    }
}
