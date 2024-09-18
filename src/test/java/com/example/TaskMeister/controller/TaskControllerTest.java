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

    @Test
    void createTask() {
        when(taskService.createTask(any(Task.class))).thenReturn(crudTask);
        Task result = taskController.createTask(crudTask);

        assertNotNull(result);
        assertEquals(crudTask.getId(), result.getId());
        assertEquals(crudTask.getName(), result.getName());
        assertEquals(crudTask.getDescription(), result.getDescription());
        assertEquals(crudTask.getStatus(), result.getStatus());
    }

    @Test
    void getAllTask() throws Exception{
        when(taskService.getAllTask()).thenReturn(taskList);
        mockMvc.perform(get("/api/task/get"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Create CRUD methods"))
            .andExpect(jsonPath("$[0].description").value("Create CRUD methods for the Task class"))
            .andExpect(jsonPath("$[0].status").value("In progress"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].name").value("Postman Tests"))
            .andExpect(jsonPath("$[1].description").value("Make automatized test with Postman"))
            .andExpect(jsonPath("$[1].status").value("Pending"));


    }

    @Test
    void getTaskById() throws Exception{
        when(taskService.getTaskById(1)).thenReturn(Optional.of(crudTask));
        mockMvc.perform(get("/api/task/get/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Create CRUD methods"))
            .andExpect(jsonPath("$.description").value("Create CRUD methods for the Task class"))
            .andExpect(jsonPath("$.status").value("In progress"));

    }

    @Test
    void updateTask() throws Exception {
        Task updatedTask = new Task(1, "Create CRUD methods", "Create CRUD methods for the Task class", "In progress", null, null);
        updatedTask.setId(1);
        updatedTask.setName("Create CRUD methods");
        updatedTask.setDescription("Create CRUD methods for the Project class");
        updatedTask.setStatus("Completed");


        mockMvc.perform(put("/api/task/put/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"Create CRUD methods\",\"description\":\"Create CRUD methods for the Project class\",\"status\":\"Completed\"}"))
                .andExpect(status().isOk());
                verify(taskService).updateTask(any(Task.class), any(Integer.class));

                
    }

    @Test
    void deleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/task/delete/1")).andExpect(status().isOk());
    }

}