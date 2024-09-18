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
                                .requestMatchers("api/auth/**").permitAll()
                                .requestMatchers("/api/user/**").authenticated()
                                .requestMatchers("/api/test/all").permitAll()
                                .requestMatchers("/api/test/user").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers("/api/test").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/api/tasks").hasAuthority("MANAGER")
                                .requestMatchers(HttpMethod.DELETE,"/api/tasks/").hasAuthority("MANAGER")
                                .requestMatchers(HttpMethod.GET,"/api/tasks/{id}").hasAuthority("USER")
                                .requestMatchers(HttpMethod.PUT,"/api/tasks/").hasAuthority("USER")
                                .requestMatchers(HttpMethod.GET,"/api/tasks").hasAnyAuthority("ADMIN", "MANAGER")
                                .requestMatchers(HttpMethod.GET,"/api/project/").hasAnyAuthority("ADMIN", "USER", "MANAGER")
                                .requestMatchers("/api/project/").hasAnyAuthority("ADMIN", "MANAGER")
                                .requestMatchers(HttpMethod.GET, "/api/user/{id}").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers("/api/user/").hasAuthority("ADMIN")
                                .anyRequest().authenticated()

                )
                .sessionManagement(sessionManager ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
