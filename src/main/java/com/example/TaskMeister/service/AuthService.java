package com.example.TaskMeister.service;

import com.example.TaskMeister.dto.request.LoginRequest;
import com.example.TaskMeister.dto.request.RegisterRequest;
import com.example.TaskMeister.dto.response.AuthResponse;
import com.example.TaskMeister.model.ERole;
import com.example.TaskMeister.model.User;
import com.example.TaskMeister.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final IUserRepository iUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest login) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

        UserDetails user = iUserRepository.findByUsername(login.getUsername()).orElseThrow();

        String token = jwtService.getTokenService(user);

        return AuthResponse.builder()
                .token(token)
                .build();
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
