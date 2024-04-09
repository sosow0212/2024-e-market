package com.server.global.querycounter;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@RequestScope
@Component
public class QueryCounter {

    private int count;

    public void increase() {
        this.count++;
    }
}
