package com.example.TaskMeister.controller;


import com.example.TaskMeister.model.Project;
import com.example.TaskMeister.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/project/")
@CrossOrigin
public class ProjectController {


private final ProjectService projectService;

public ProjectController(ProjectService projectService){
    this.projectService = projectService;
}
@PostMapping(path = "")
    public Project createProject(@RequestBody Project project) {
return projectService.createProject(project);

}
@GetMapping(path = "")
        public List<Project> getAllProject(){
    return projectService.getAllProject();
    }

    @GetMapping(path = "/{id}")
    public Optional<Project> getProjectById(@PathVariable Long id){
    return projectService.getProjectById(id);
}

@PutMapping(path = "/{id}")
    public void updateProject(@RequestBody Project project, @PathVariable int id){
    projectService.updateProject(project, id);
}

@DeleteMapping(path = "/{id}")
    public String deleteProjectById(@PathVariable Long id){
    boolean ok = projectService.deleteProject(id);

    if (ok){
        return "Project with id" + id + "was delete";
    }else{
        return "Project with id" + id + "not found";
    }
}



}
