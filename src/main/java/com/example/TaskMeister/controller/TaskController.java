package com.example.TaskMeister.controller;

import com.example.TaskMeister.model.Task;
import com.example.TaskMeister.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/task")
@CrossOrigin
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping(path = "post")
        public Task createTask(@RequestBody Task task) {
            return taskService.createTask(task);
        }

    @GetMapping(path = "get")
        public List<Task> getAllTask() {
            return taskService.getAllTask();
        }
        
    @GetMapping(path = "get/{id}")
        public Optional<Task> getTaskById(@PathVariable int id) {
            return taskService.getTaskById(id);
        }
        
    @PutMapping(path = "put/{id}")
        public void updateTask(@RequestBody Task task, @PathVariable int id) {
            taskService.updateTask(task, id);
        }
    
    @DeleteMapping(path = "delete/{id}")
        public void deleteTask(@PathVariable int id) {
            taskService.deleteTask(id);
        }    
}
