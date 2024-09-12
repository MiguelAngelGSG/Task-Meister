package com.example.TaskMeister.config;

import com.example.TaskMeister.jwt.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthTokenFilter authTokenFilter;
    private final ApplicationConfig applicationConfig;

    public SecurityConfig(AuthTokenFilter authTokenFilter, ApplicationConfig applicationConfig) {
        this.authTokenFilter = authTokenFilter;
        this.applicationConfig = applicationConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
    }
}
