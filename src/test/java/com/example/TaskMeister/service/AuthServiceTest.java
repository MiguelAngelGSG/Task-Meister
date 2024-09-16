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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

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
    void loginSuccess() {
        // Створення запиту на логін
        LoginRequest loginRequest = new LoginRequest("testuser", "password");

        // Створення користувача з відповідним username та role
        User user = User.builder()
                .username("testuser")
                .password("encodedPassword") // Зазначте закодований пароль
                .email("testuser@example.com")
                .role(ERole.USER) // Ініціалізуйте роль
                .build();

        // Мокування результату для findByUsername
        when(iUserRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        // Мокування результату для getTokenService
        when(jwtService.getTokenService(any(UserDetails.class))).thenReturn("token");

        // Мокування успішної аутентифікації
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(user, "password", user.getAuthorities()));

        // Виконання логіну
        AuthResponse response = authService.login(loginRequest);

        // Перевірка результатів
        assertNotNull(response);
        assertEquals("token", response.getToken());
        verify(authenticationManager).authenticate(any());
    }



    @Test
    void registerSuccess() {
        RegisterRequest registerRequest = new RegisterRequest("newuser", "email@example.com", "password", ERole.USER);

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtService.getTokenService(any(UserDetails.class))).thenReturn("token");

        AuthResponse response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals("token", response.getToken());
        assertEquals(ERole.USER, response.getRole());
        verify(iUserRepository).save(any(User.class));
    }

    @Test
    void registerWithoutRole() {
        RegisterRequest registerRequest = new RegisterRequest("newuser", "email@example.com", "password", null);

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtService.getTokenService(any(UserDetails.class))).thenReturn("token");

        AuthResponse response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals("token", response.getToken());
        assertEquals(ERole.USER, response.getRole());
        verify(iUserRepository).save(any(User.class));
    }
}