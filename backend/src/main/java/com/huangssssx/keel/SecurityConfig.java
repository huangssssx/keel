package com.huangssssx.keel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // 禁用 CSRF 保护
            .authorizeRequests()
            .requestMatchers("/authenticate").permitAll() // 允许所有用户访问此端点
            .requestMatchers("/hello").permitAll() // 允许所有用户访问 /hello 端点
            .anyRequest().authenticated(); // 其他请求需要身份验证
        return http.build();
    }
}
