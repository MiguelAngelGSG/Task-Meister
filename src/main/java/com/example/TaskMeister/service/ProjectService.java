package com.example.TaskMeister.service;

import com.example.TaskMeister.model.Project;
import com.example.TaskMeister.repositories.IProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final IProjectRepository iProjectRepository;

    public ProjectService(IProjectRepository iProjectRepository){
        this.iProjectRepository = iProjectRepository;
    }

    public  Project createProject(Project project){
        return iProjectRepository.save(project);
    }


    public List<Project> getAllProject(){
        try {
            return iProjectRepository.findAll();
        } catch (Exception e){
           throw new RuntimeException("Error retrieving project.", e);
        }
    }

    public Optional<Project> getProjectById(Long id){
try {
    return iProjectRepository.findById(id);
}catch (Exception e){
    throw new RuntimeException("Error retrieving details.", e);
}
    }

    public void updateProject(Project project, int id){
        project.setId(id);
        iProjectRepository.save(project);

    }

    public boolean deleteProject(Long id){
        try{
            iProjectRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
