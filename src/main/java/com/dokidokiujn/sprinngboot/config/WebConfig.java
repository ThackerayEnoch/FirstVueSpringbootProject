package com.dokidokiujn.sprinngboot.config;

import com.dokidokiujn.sprinngboot.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //应用拦截器
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns(
                        "/**"
                )
                .excludePathPatterns(
                        "/user/login",
                        "/user/register",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v3/**",
                        "/swagger-ui.html/**",
                        "/swagger-ui/**",
                        "/swagger-ui/index.html/**",
                        "/index.html"
                );
    }
}
