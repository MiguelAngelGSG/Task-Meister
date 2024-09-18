package com.example.TaskMeister.repositories;

import com.example.TaskMeister.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<Task, Integer> {
}
