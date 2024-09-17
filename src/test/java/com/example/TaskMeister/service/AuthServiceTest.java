package com.example.TaskMeister.service;

import com.example.TaskMeister.dto.request.LoginRequest;
import com.example.TaskMeister.dto.request.RegisterRequest;
import com.example.TaskMeister.dto.response.AuthResponse;
import com.example.TaskMeister.model.ERole;
import com.example.TaskMeister.model.User;
import com.example.TaskMeister.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private IUserRepository iUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("username", "password");
        User user = new User(); // Create a User object here
        user.setUsername("username");
        user.setPassword("password");
        String token = "dummyToken";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null); // Simulate successful authentication
        when(iUserRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(jwtService.getTokenService(any(User.class))).thenReturn(token);

        // Act
        AuthResponse response = authService.login(loginRequest);

        // Assert
        assertEquals(token, response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(iUserRepository).findByUsername(loginRequest.getUsername());
        verify(jwtService).getTokenService(any(User.class)); // Use any() matcher here
    }

    @Test
    void testRegister() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("username", "email@example.com", "password", ERole.USER);
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword("encodedPassword");
        user.setRole(registerRequest.getRole());
        String token = "dummyToken";

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(iUserRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.getTokenService(any(User.class))).thenReturn(token);

        // Act
        AuthResponse response = authService.register(registerRequest);

        // Assert
        assertEquals(token, response.getToken());
        assertEquals(registerRequest.getRole(), response.getRole());
        verify(passwordEncoder).encode(registerRequest.getPassword());
        verify(iUserRepository).save(any(User.class));
        verify(jwtService).getTokenService(any(User.class)); // Use any() matcher here
    }
}
