package com.example.TaskMeister.controller;

import com.example.TaskMeister.model.Task;
import com.example.TaskMeister.service.TaskService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TaskControllerTest {
    @Mock
    private TaskService taskService;
    @InjectMocks
    private TaskController taskController;
    private MockMvc mockMvc;
    
    private Task crudTask;
    private Task postmanTask;
    private List<Task> taskList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();

        crudTask = new Task(1, "Create CRUD methods", "Create CRUD methods for the Task class", "In progress", null, null);
        postmanTask = new Task(2, "Postman Tests", "Make automatized test with Postman", "Pending", null, null);

        taskList.add(crudTask);
        taskList.add(postmanTask);

    }

    


}
