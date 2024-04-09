package com.server.global.querycounter;

import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public class ConnectionProxyHandler implements InvocationHandler {

    private static final String QUERY_PREPARE_STATEMENT = "prepareStatement";

    private final Object connection;
    private final QueryCounter queryCounter;

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        countQuery(method);
        return method.invoke(connection, args);
    }

    private void countQuery(final Method method) {
        if (isPrepareStatementMethod(method) && isRequest()) {
            queryCounter.increase();
        }
    }

    private boolean isPrepareStatementMethod(final Method method) {
        return method.getName()
                .equals(QUERY_PREPARE_STATEMENT);
    }

    private boolean isRequest() {
        return RequestContextHolder.getRequestAttributes() != null;
    }
}
