package com.example.TaskMeister.config;

import com.example.TaskMeister.jwt.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final AuthTokenFilter authTokenFilter;

    // Конструктор без @RequiredArgsConstructor
    public WebSecurityConfig(AuthenticationProvider authenticationProvider, AuthTokenFilter authTokenFilter) {
        this.authenticationProvider = authenticationProvider;
        this.authTokenFilter = authTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/api/auth/login").permitAll()
                                .requestMatchers("/api/auth/register").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/user/").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/api/user/").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/user/").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/user/").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/project/").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/project/").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers(HttpMethod.PUT, "/api/project/").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers(HttpMethod.DELETE, "/api/project/").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers(HttpMethod.GET, "/api/task").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers(HttpMethod.GET, "/api/task/{id}").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/task/").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.PUT, "/api/task/{id}").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/task/").hasRole("MANAGER")

                )
                .sessionManagement(sessionManager ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
