package com.market.member.ui.auth.interceptor;

import org.springframework.util.PathMatcher;

public class PathRequest {

    private final String path;
    private final HttpMethod httpMethod;

    public PathRequest(final String path, final HttpMethod httpMethod) {
        this.path = path;
        this.httpMethod = httpMethod;
    }

    public boolean matches(final PathMatcher pathMatcher,
                           final String targetPath,
                           final String pathMethod) {
        return pathMatcher.match(path, targetPath) &&
                httpMethod.matches(pathMethod);
    }
}
