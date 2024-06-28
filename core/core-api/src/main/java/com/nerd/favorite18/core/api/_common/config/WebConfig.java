package com.nerd.favorite18.core.api._common.config;

import com.nerd.favorite18.core.api._common.intercepter.AuthenticationInterceptor;
import com.nerd.favorite18.core.api._common.intercepter.AuthorizationInterceptor;
import com.nerd.favorite18.core.api._common.resolver.UserSessionResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final AuthenticationInterceptor authenticationInterceptor;
    private final AuthorizationInterceptor authorizationInterceptor;
    private final UserSessionResolver userSessionResolver;

    private final List<String> OPEN_API = List.of(
            "/open-api/**"
    );

    private final List<String> ADMIN_API = List.of(
            "/admin-api/**"
    );

    private final List<String> DEFAULT_EXCLUDE = List.of(
            "/",
            "/favicon.ico",
            "/health",
            "/error",
            "/enum/**"
    );

    private final List<String> SWAGGER = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .excludePathPatterns(OPEN_API)
                .excludePathPatterns(DEFAULT_EXCLUDE)
                .excludePathPatterns(SWAGGER);

        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns(ADMIN_API);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userSessionResolver);
    }
}
