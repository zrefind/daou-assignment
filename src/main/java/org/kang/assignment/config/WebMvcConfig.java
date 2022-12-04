package org.kang.assignment.config;

import lombok.RequiredArgsConstructor;
import org.kang.assignment.common.interceptor.IpAddressAccessInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final IpAddressAccessInterceptor ipAddressAccessInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipAddressAccessInterceptor)
                .order(1)
                .addPathPatterns("/**");
    }

}
