package com.example.TaskMeister.service;

import com.example.TaskMeister.model.Project;
import com.example.TaskMeister.repositories.IProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {

    @Mock
 private    IProjectRepository iProjectRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);


    }

    @Test
    void createProject(){
        Project project1 = new Project("Project One",1);

        when(iProjectRepository.save(any(Project.class))).thenReturn(project1);

        Project createdProject = projectService.createProject(project1);

        assertEquals(project1.getName(), createdProject.getName());
        verify(iProjectRepository, times(1)).save(project1);
    }

    @Test
    void getAllProject(){

        Project project1 = new Project("Project one",1);
        Project project2 = new Project("Project two",2);

        List<Project> projectList = Arrays.asList(project1,project2);

        when(iProjectRepository.findAll()).thenReturn(projectList);

        List<Project> result = projectService.getAllProject();

        assertEquals(2, result.size());
        assertEquals("Project one",result.get(0).getName());
        assertEquals("Project two", result.get(1).getName());
        verify(iProjectRepository, times(1)).findAll();

    }

    @Test
    void getProjectById(){

        Project project1 = new Project("Project one",1);

        when(iProjectRepository.findById(1L)).thenReturn(Optional.of(project1));

        Optional<Project> retrievedProject = projectService.getProjectById(1L);

        assertTrue(retrievedProject.isPresent());
        assertEquals("Project one", retrievedProject.get().getName());

        verify(iProjectRepository, times(1)).findById(1L);

    }

    @Test
    void updateProject(){

        Project project2 = new Project("update two",2);

        projectService.updateProject(project2,2);

        verify(iProjectRepository, times(1)).save(project2);
        assertEquals(2, project2.getId());
        assertEquals("update two", project2.getName());
    }

    @Test
    void deleteProjectById(){
    doNothing().when(iProjectRepository).deleteById(2L);

    boolean result = projectService.deleteProject(2L);

    assertTrue(result);

    verify(iProjectRepository, times(1)).deleteById(2L);
    }
}
