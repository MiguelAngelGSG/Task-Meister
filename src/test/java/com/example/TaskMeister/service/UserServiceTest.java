package com.example.TaskMeister.service;

import com.example.TaskMeister.model.User;
import com.example.TaskMeister.model.ERole;
import com.example.TaskMeister.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private IUserRepository iUserRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        User user = User.builder()
                .id(1L)
                .username("Juan")
                .password("password123")
                .email("juan@example.com")
                .role(ERole.USER)
                .build();
        users.add(user);
        when(iUserRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Juan", result.get(0).getUsername());
    }

    @Test
    void getUserById() {
        User user = User.builder()
                .id(1L)
                .username("Juan")
                .password("password123")
                .email("juan@example.com")
                .role(ERole.USER)
                .build();
        when(iUserRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Juan", result.getUsername());
    }

    @Test
    void deleteUser() {
        Long userId = 1L;
        doNothing().when(iUserRepository).deleteById(userId);

        userService.delteUser(userId);  // Використовуйте правильний метод

        verify(iUserRepository, times(1)).deleteById(userId);
    }

    @Test
    void updateUser() {
        User existingUser = User.builder()
                .id(1L)
                .username("Juan")
                .password("password123")
                .email("juan@example.com")
                .role(ERole.USER)
                .build();

        User updatedUser = User.builder()
                .id(1L)
                .username("Juan Updated")
                .password("newpassword123")
                .email("juan.updated@example.com")
                .role(ERole.ADMIN)
                .build();

        when(iUserRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(iUserRepository.save(updatedUser)).thenReturn(updatedUser);

        User result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Juan Updated", result.getUsername());
        assertEquals("newpassword123", result.getPassword());
        assertEquals("juan.updated@example.com", result.getEmail());
        assertEquals(ERole.ADMIN, result.getRole());
    }
}
