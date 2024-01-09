package com.market.member.ui.auth.interceptor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class PathContainerTest {

    private PathContainer container;

    @BeforeEach
    void setup() {
        container = new PathContainer();
    }

    @Test
    void include로_등록한_메서드와_uri가_같으면_false를_반환한다() {
        // given
        String uri = "/url/test";
        HttpMethod method = HttpMethod.GET;

        container.addIncludePatterns(uri, method);

        // when
        boolean result = container.isNotIncludedPath(uri, method.name());

        // then
        assertThat(result).isFalse();
    }

    @Test
    void include로_등록한_메서드와_uri가_다르면_true를_반환한다() {
        // given
        String uri = "/url/test";
        HttpMethod method = HttpMethod.GET;

        container.addIncludePatterns(uri, method);

        // when
        boolean result = container.isNotIncludedPath(uri + "wrong", HttpMethod.GET.name());

        // then
        assertThat(result).isTrue();
    }

    @Test
    void exclude로_등록한_메서드와_uri가_같으면_true를_반환한다() {
        // given
        String uri = "/url/test";
        HttpMethod method = HttpMethod.GET;

        container.addExcludePatterns(uri, method);

        // when
        boolean result = container.isNotIncludedPath(uri, method.name());

        // then
        assertThat(result).isTrue();
    }
}
