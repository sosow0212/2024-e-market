package com.market.auth.ui.interceptor;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class HttpMethodTest {

    @Test
    void http_메서드가_같은지_확인한다() {
        // given
        HttpMethod httpMethod = HttpMethod.GET;

        // when
        boolean result = httpMethod.matches(HttpMethod.GET.name());

        // then
        assertThat(result).isTrue();
    }
}
