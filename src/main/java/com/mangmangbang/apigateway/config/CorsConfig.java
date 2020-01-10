package com.mangmangbang.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 *
 * 跨域配置
 * created by zhangjingchuan on 2020/1/10
 *
 * c-cross o-origin r-resource s-sharing
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter crosFilter(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        //设置是否支持cookie跨域
        config.setAllowCredentials(true);
        //放置原始域
        //*表示所有
        config.setAllowedOrigins(Arrays.asList("*"));
        //允许的头
        config.setAllowedHeaders(Arrays.asList("*"));
        //允许的请求方式，get/post
        config.setAllowedMethods(Arrays.asList("*"));
        //缓存时间
        config.setMaxAge(300l);
        source.registerCorsConfiguration("/**",config);

        return new CorsFilter(source);
    }
}
