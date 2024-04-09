package com.server.global.querycounter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
@Component
public class QueryCounterInterceptor implements HandlerInterceptor {

    private static final String QUERY_COUNT_MESSAGE = "{} {} {}, QUERY_COUNT: {}";
    private static final String WARN_MESSAGE = "쿼리가 {}번 이상 실행되었습니다.";
    private static final int WARN_QUERY_COUNT = 4;

    private final QueryCounter queryCounter;

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {
        int queryCount = queryCounter.getCount();
        log.info(QUERY_COUNT_MESSAGE, request.getMethod(), request.getRequestURI(), response.getStatus(), queryCount);

        if (queryCount >= WARN_QUERY_COUNT) {
            log.warn(WARN_MESSAGE, WARN_QUERY_COUNT);
        }
    }
}
