package com.example.TaskMeister.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Task")


public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="project_id")
    private Project project;


public Task(int id, String name, String description, String status, User user, Project project) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.user = user;
        this.project = project;
    }

public int getId() {
    return id;
}

public void setId(int id) {
    this.id = id;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getDescription() {
    return description;
}

public void setDescription(String description) {
    this.description = description;
}

public String getStatus() {
    return status;
}

public void setStatus(String status) {
    this.status = status;
}

public User getUser() {
    return user;
}

public void setUser(User user) {
    this.user = user;
}

public Project getProject() {
    return project;
}

public void setProject(Project project) {
    this.project = project;
}
}

