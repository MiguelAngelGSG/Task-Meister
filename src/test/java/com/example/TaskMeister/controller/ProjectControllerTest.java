package com.example.TaskMeister.controller;

import com.example.TaskMeister.model.Project;
import com.example.TaskMeister.repositories.IProjectRepository;
import com.example.TaskMeister.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;



import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



public class ProjectControllerTest {

    @Mock
private ProjectService projectService;
private MockMvc mockMvc;


@InjectMocks
private ProjectController projectController;


@BeforeEach
   void SetUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();

}

@Test
    void createProject() throws Exception{

    Project project1 = new Project("New Project",1);

    when(projectService.createProject(any(Project.class))).thenReturn(project1);

    ObjectMapper objectMapper = new ObjectMapper();
    String projectJson = objectMapper.writeValueAsString(project1);

    mockMvc.perform(post("/api/project")
            .contentType(MediaType.APPLICATION_JSON)
            .content(projectJson))
    .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("New Project"))
            .andExpect(jsonPath("$.id").value(1));
}

@Test

    void getAllProject() throws Exception{

    Project project1 = new Project("project 1",1);
    Project project2 = new Project("project two",2);

    List<Project> projectList = Arrays.asList(project1,project2);

    when(projectService.getAllProject()).thenReturn(projectList);

    mockMvc.perform(get("/api/project"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("project 1"))
            .andExpect(jsonPath("$[1].name").value("project two"));


}
@Test
    void getProjectById() throws Exception{

    Project project1 = new Project("project 1",1);

    when(projectService.getProjectById(1L)).thenReturn(Optional.of(project1));

    mockMvc.perform(get("/api/project/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("project 1"))
            .andExpect(jsonPath("$.id").value(1));

}

@Test
    void updateProject() throws Exception{

    Project project2 = new Project("Update project",2);

    ObjectMapper objectMapper = new ObjectMapper();
    String projectJson = objectMapper.writeValueAsString(project2);

    mockMvc.perform(put("/api/project/2")
            .contentType(MediaType.APPLICATION_JSON)
            .content(projectJson))
            .andExpect(status().isOk());

    verify(projectService, times(1)).updateProject(any(Project.class),eq(2));


}
@Test
    void deleteProject() throws Exception{

    when(projectService.deleteProject(1L)).thenReturn(true);

    mockMvc.perform(delete("/api/project/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Project with id1was delete"));

    verify(projectService, times(1)).deleteProject(1L);

}

}







