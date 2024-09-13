package com.example.TaskMeister.repositories;

import com.example.TaskMeister.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProjectRepository extends JpaRepository<Project, Long > {
}
