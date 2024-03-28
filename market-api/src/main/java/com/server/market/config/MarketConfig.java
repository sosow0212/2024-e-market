package com.server.market.config;

import com.server.market.ui.support.resolver.ViewCountArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class MarketConfig implements WebMvcConfigurer {

    private final ViewCountArgumentResolver viewCountArgumentResolver;

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(viewCountArgumentResolver);
    }
}
