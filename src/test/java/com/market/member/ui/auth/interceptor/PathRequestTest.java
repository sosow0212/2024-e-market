package com.market.member.ui.auth.interceptor;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.util.AntPathMatcher;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class PathRequestTest {

    @Test
    void uri와_method가_같은지_확인한다() {
        // given
        String path = "/path";
        HttpMethod method = HttpMethod.GET;

        PathRequest pathRequest = new PathRequest(path, method);

        // when
        boolean result = pathRequest.matches(new AntPathMatcher(), path, method.name());

        // then
        assertThat(result).isTrue();
    }
}
