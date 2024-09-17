package com.example.TaskMeister.controller;

import com.example.TaskMeister.dto.request.LoginRequest;
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
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    void testLoginSuccess() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("username", "password");
        AuthResponse expectedResponse = new AuthResponse("mocked-jwt-token");
        when(authService.login(loginRequest)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<AuthResponse> response = authController.login(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(authService, times(1)).login(loginRequest);
    }

    @Test
    void testLoginFailure() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("username", "wrong-password");
        when(authService.login(loginRequest)).thenThrow(new RuntimeException("Unauthorized"));

        // Act
        ResponseEntity<AuthResponse> response = authController.login(loginRequest);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(authService, times(1)).login(loginRequest);
    }
}
