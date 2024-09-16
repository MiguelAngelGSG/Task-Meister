package com.example.TaskMeister.Service;

import com.example.TaskMeister.model.Task;
import com.example.TaskMeister.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestService
@RequestMapping("/api/task")
@CrossOrigin
public class TaskController {

    private final TaskService taskService;

    public TaskService(TaskService taskService){
        this.taskService = taskService;
    }
}
