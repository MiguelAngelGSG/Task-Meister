package com.example.TaskMeister.controller;

import com.example.TaskMeister.model.User;
import com.example.TaskMeister.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping(path = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User newUser) {
        return ResponseEntity.ok(userService.updateUser(id, newUser));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delteUser(id);
        return ResponseEntity.ok().build();
    }
}