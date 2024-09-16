package com.example.TaskMeister.controller;

import com.example.TaskMeister.dto.request.LoginRequest;
import com.example.TaskMeister.dto.request.RegisterRequest;
import com.example.TaskMeister.dto.response.AuthResponse;
import com.example.TaskMeister.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @Mock
    private AuthService authService;  // Мок для сервісу

    @InjectMocks
    private AuthController authController;  // Тестуємо контролер

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Ініціалізація моків перед кожним тестом
    }

    @Test
    void login() {
        // Створюємо тестові дані для запиту
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("testpassword");

        // Створюємо відповідь, яку очікуємо отримати після логіна
        AuthResponse authResponse = AuthResponse.builder()
                .token("test-jwt-token")
                .build();

        // Імітуємо поведінку сервісу
        when(authService.login(loginRequest)).thenReturn(authResponse);

        // Викликаємо метод логіна в контролері
        ResponseEntity<AuthResponse> response = authController.login(loginRequest);

        // Перевіряємо, що статус відповіді - OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Перевіряємо, що токен у відповіді правильний
        assertEquals("test-jwt-token", response.getBody().getToken());
    }

    @Test
    void register() {
        // Створюємо тестові дані для запиту реєстрації
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("newuser");
        registerRequest.setPassword("newpassword");

        // Створюємо відповідь, яку очікуємо отримати після реєстрації
        AuthResponse authResponse = AuthResponse.builder()
                .token("new-jwt-token")
                .build();

        // Імітуємо поведінку сервісу
        when(authService.register(registerRequest)).thenReturn(authResponse);

        // Викликаємо метод реєстрації в контролері
        ResponseEntity<AuthResponse> response = authController.register(registerRequest);

        // Перевіряємо, що статус відповіді - OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Перевіряємо, що токен у відповіді правильний
        assertEquals("new-jwt-token", response.getBody().getToken());
    }
}
