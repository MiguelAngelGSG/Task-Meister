package com.example.TaskMeister.service;

import com.example.TaskMeister.model.Task;
import com.example.TaskMeister.repositories.ITaskRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskServiceTest {
    @Mock private ITaskRepository iTaskRepository;
    @InjectMocks private TaskService taskService;

    private Task crudTask;
    private Task postmanTask;

    private List<Task> taskList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        crudTask = new Task(1, "Create CRUD methods", "Create CRUD methods for the Task class", "In progress", null, null);
        postmanTask = new Task(2, "Postman Tests", "Make automatized test with Postman", "Pending", null, null);

        taskList.add(crudTask);
        taskList.add(postmanTask);
    }

    @Test
    void createTask() {
        when(iTaskRepository.save(ArgumentMatchers.any(Task.class))).thenReturn(postmanTask);

        Task result = taskService.createTask(postmanTask);
        assertEquals(2, result.getId());
        assertEquals("Postman Tests", result.getName());
        assertEquals("Make automatized test with Postman", result.getDescription());
        assertEquals("Pending", result.getStatus());
    }

    @Test
    void getAllTask() {
        when(iTaskRepository.findAll()).thenReturn(taskList);

        List<Task> result = taskService.getAllTask();

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Create CRUD methods", result.get(0).getName());
        assertEquals("Create CRUD methods for the Task class", result.get(0).getDescription());
        assertEquals("In progress", result.get(0).getStatus());

        assertEquals(2, result.get(1).getId());
        assertEquals("Postman Tests", result.get(1).getName());
        assertEquals("Make automatized test with Postman", result.get(1).getDescription());
        assertEquals("Pending", result.get(1).getStatus());
    }

    @Test
    public void getTaskById(){
        when(iTaskRepository.findById(2)).thenReturn(Optional.of(postmanTask));
        Optional<Task> taskId = taskService.getTaskById(2);
        assertEquals("Postman Tests", taskId.get().getName());
          }

          @Test
    void updatedTask() {
         when(iTaskRepository.save(any(Task.class))).thenReturn(postmanTask);
    Task update = postmanTask;
    update.setStatus("Completed");

    taskService.updateTask(update, 2);
    assertEquals(2, update.getId());
    assertEquals("Postman Tests", update.getName());
    assertEquals("Make automatized test with Postman", update.getDescription());
    assertEquals("Completed", update.getStatus());
    

    verify(iTaskRepository, times(1)).save(update);

    }

    @Test
  void deleteTask() {
    when(iTaskRepository.findById(2)).thenReturn(Optional.of(postmanTask));

    taskService.deleteTask(2);

    verify(iTaskRepository, times(1)).deleteById(2);      
    }
}
