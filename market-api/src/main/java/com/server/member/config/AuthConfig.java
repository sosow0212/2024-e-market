package com.server.member.config;

import com.server.member.ui.auth.interceptor.LoginValidCheckerInterceptor;
import com.server.member.ui.auth.interceptor.ParseMemberIdFromTokenInterceptor;
import com.server.member.ui.auth.interceptor.PathMatcherInterceptor;
import com.server.member.ui.auth.support.resolver.AuthArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.server.member.ui.auth.interceptor.HttpMethod.ANY;
import static com.server.member.ui.auth.interceptor.HttpMethod.DELETE;
import static com.server.member.ui.auth.interceptor.HttpMethod.GET;
import static com.server.member.ui.auth.interceptor.HttpMethod.OPTIONS;
import static com.server.member.ui.auth.interceptor.HttpMethod.PATCH;
import static com.server.member.ui.auth.interceptor.HttpMethod.POST;

@RequiredArgsConstructor
@Configuration
public class AuthConfig implements WebMvcConfigurer {

    private final AuthArgumentResolver authArgumentResolver;
    private final ParseMemberIdFromTokenInterceptor parseMemberIdFromTokenInterceptor;
    private final LoginValidCheckerInterceptor loginValidCheckerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(parseMemberIdFromTokenInterceptor());
        registry.addInterceptor(loginValidCheckerInterceptor());
    }

    private HandlerInterceptor parseMemberIdFromTokenInterceptor() {
        return new PathMatcherInterceptor(parseMemberIdFromTokenInterceptor)

                .excludePathPattern("/**", OPTIONS)
                .addPathPatterns("/admin/**", ANY);
    }

    /**
     * @AuthMember를 통해서 인증이 필요한 경우에 해당 메서드에 URI를 추가해주면 된다.
     * 추가를 해야지 인증,인가 가능
     */
    private HandlerInterceptor loginValidCheckerInterceptor() {
        return new PathMatcherInterceptor(loginValidCheckerInterceptor)
                .excludePathPattern("/**", OPTIONS)
                .addPathPatterns("/api/boards/**", GET, POST, PATCH, DELETE)
                .addPathPatterns("/api/boards/**/comments", GET, POST, PATCH, DELETE)
                .addPathPatterns("/api/categories/**", GET, POST, PATCH, DELETE)
                .addPathPatterns("/api/categories/**/products/**", GET, POST, PATCH, DELETE)
                .addPathPatterns("/api/coupons/**", GET, POST, PATCH, DELETE)
                .addPathPatterns("/api/members/**", GET, POST, PATCH, DELETE)
                .addPathPatterns("/api/vouchers/**", GET, POST, DELETE);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
    }
}
