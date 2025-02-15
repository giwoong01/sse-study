package com.sse.sse_study_backend.global.config;

import com.sse.sse_study_backend.global.filter.LoginTokenCheckFilter;
import com.sse.sse_study_backend.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final TokenProvider tokenProvider;

    @Bean
    public FilterRegistrationBean<LoginTokenCheckFilter> loginTokenCheckFilter() {
        FilterRegistrationBean<LoginTokenCheckFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginTokenCheckFilter(tokenProvider));
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

}
