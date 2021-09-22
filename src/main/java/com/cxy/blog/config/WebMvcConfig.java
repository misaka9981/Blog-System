package com.cxy.blog.config;

import com.cxy.blog.common.interceptor.AdminInterceptor;
import com.cxy.blog.common.interceptor.LoginInterceptor;
import com.cxy.blog.common.resolver.CustomAugmentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

 
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
                 String[] frontLoginPaths = {
                "/api/comment/save",
                "/api/logout",
                "/api/user/changePass"
        };
                 registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/api 
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(" 
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CustomAugmentResolver());
    }
}
