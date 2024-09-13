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
        User user = new User();
        user.setId(1L);
        user.setUsername("Juan");
        user.setPassword("password123");
        user.setEmail("juan@example.com");
        user.setRole(ERole.USER);
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
        User user = new User();
        user.setId(1L);
        user.setUsername("Juan");
        user.setPassword("password123");
        user.setEmail("juan@example.com");
        user.setRole(ERole.USER);
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

        userService.delteUser(userId);

        verify(iUserRepository, times(1)).deleteById(userId);
    }

    @Test
    void updateUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("Juan");
        existingUser.setPassword("password123");
        existingUser.setEmail("juan@example.com");
        existingUser.setRole(ERole.USER);

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setUsername("Juan Updated");
        updatedUser.setPassword("newpassword123");
        updatedUser.setEmail("juan.updated@example.com");
        updatedUser.setRole(ERole.ADMIN);

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
