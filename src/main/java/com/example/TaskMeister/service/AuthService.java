package com.example.TaskMeister.service;

import com.example.TaskMeister.dto.request.LoginRequest;
import com.example.TaskMeister.dto.request.RegisterRequest;
import com.example.TaskMeister.dto.response.AuthResponse;
import com.example.TaskMeister.model.ERole;
import com.example.TaskMeister.model.User;
import com.example.TaskMeister.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final IUserRepository userRepository;
    private final IUserRepository iUserRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthResponse login(LoginRequest login) {
        log.info("Attempting to authenticate user: {}", login.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.getTokenService(userDetails);

            // Отримання ролі користувача
            User user = userRepository.findByUsername(login.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            log.info("User authenticated successfully: {}", login.getUsername());
            return AuthResponse.builder()
                    .token(token)
                    .role(user.getRole())
                    .build();
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user: {}", login.getUsername(), e);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid username or password");
        }
    }


    public AuthResponse register(RegisterRequest register) {
        ERole role = (register.getRole() != null) ? register.getRole() : ERole.USER;

        User user = User.builder()
                .username(register.getUsername())
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .role(role)
                .build();

        iUserRepository.save(user);

        String token = jwtService.getTokenService(user);

        return AuthResponse.builder()
                .token(token)
                .role(role)
                .build();
    }
}