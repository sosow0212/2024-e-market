package com.server.member.ui.auth.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class PathMatcherInterceptor implements HandlerInterceptor {

    private final HandlerInterceptor handlerInterceptor;
    private final PathContainer pathContainer;

    public PathMatcherInterceptor(final HandlerInterceptor handlerInterceptor) {
        this.handlerInterceptor = handlerInterceptor;
        this.pathContainer = new PathContainer();
    }

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        if (pathContainer.isNotIncludedPath(request.getServletPath(), request.getMethod())) {
            return true;
        }
        return handlerInterceptor.preHandle(request, response, handler);
    }

    public PathMatcherInterceptor addPathPatterns(final String pathPattern, final HttpMethod... httpMethod) {
        pathContainer.addIncludePatterns(pathPattern, httpMethod);
        return this;
    }

    public PathMatcherInterceptor excludePathPattern(final String pathPattern, final HttpMethod... pathMethod) {
        pathContainer.addExcludePatterns(pathPattern, pathMethod);
        return this;
    }
}
