package com.example.TaskMeister.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.TaskMeister.model.Task;
import com.example.TaskMeister.repositories.ITaskRepository;

@Service
public class TaskService {

    private final ITaskRepository iTaskRepository;

    public TaskService(ITaskRepository iTaskRepository) {
        this.iTaskRepository = iTaskRepository;
    }

    public Task createTask(Task newTask) {
        return iTaskRepository.save(newTask);
    }

    public List<Task> getAllTask() {
        return (List<Task>) iTaskRepository.findAll();
    }

    public Optional<Task> getTaskById(int id) {
        return iTaskRepository.findById(id);
    }

    public void updateTask(Task task, int id) {
        task.setId(id);
        iTaskRepository.save(task);
    }

    public void deleteTask(int id) {
        iTaskRepository.deleteById(id);
    }
}