package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")          // 匹配所有接口路径
                .allowedOriginPatterns("*") // 允许的源（* 表示全部）
                .allowedMethods("*")       // 允许的HTTP方法（GET/POST等）
                .allowedHeaders("*")       // 允许的请求头
                .allowCredentials(true)    // 允许携带Cookie
                .maxAge(3600);             // 预检请求缓存时间（秒）‌:ml-citation{ref="2,3" data="citationList"}
    }
}

