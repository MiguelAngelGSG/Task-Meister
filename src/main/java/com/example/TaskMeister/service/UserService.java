package com.example.TaskMeister.service;

import com.example.TaskMeister.model.User;
import com.example.TaskMeister.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    private final IUserRepository iUserRepository;

    public UserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public List<User> getAllUsers() {
        return (List<User>) iUserRepository.findAll();
    }
    public User getUserById(Long id) {
        return iUserRepository.findById(id).orElseThrow();
    }
    public void delteUser(Long id) {
        iUserRepository.deleteById(id);
    }
    public User updateUser(Long id, User newUser) {
        newUser.setId(id);
        return iUserRepository.save(newUser);
    }
}