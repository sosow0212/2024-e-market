package com.server.global.querycounter;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

@RequiredArgsConstructor
@Aspect
@Component
public class QueryCounterAop {

    private final QueryCounter queryCounter;

    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Object getConnection(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object connection = proceedingJoinPoint.proceed();

        return Proxy.newProxyInstance(
                connection.getClass().getClassLoader(),
                connection.getClass().getInterfaces(),
                new ConnectionProxyHandler(connection, queryCounter)
        );
    }
}
