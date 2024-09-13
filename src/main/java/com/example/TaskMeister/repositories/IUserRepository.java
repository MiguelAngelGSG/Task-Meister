package com.example.TaskMeister.repositories;

import com.example.TaskMeister.model.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserRepository extends CrudRepository<User, Long> {
}
