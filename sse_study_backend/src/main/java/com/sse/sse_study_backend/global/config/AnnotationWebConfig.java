package com.sse.sse_study_backend.global.config;

import com.sse.sse_study_backend.global.annotationresolver.AuthenticatedMemberIdArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AnnotationWebConfig implements WebMvcConfigurer {

    private final AuthenticatedMemberIdArgumentResolver authenticatedMemberIdArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticatedMemberIdArgumentResolver);
    }
}

