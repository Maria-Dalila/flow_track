package com.dalila.flow_track.model.task;

import com.dalila.flow_track.model.user.User;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name ="tbl_task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private String description;
    private TaskStatus status;
    private String createdBy;

    @ManyToMany(mappedBy = "tasks", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<User> assignedUsers = new ArrayList<>();

    public Task(){
    }

    public Task(UUID id, String title, String description, TaskStatus status, String createdBy, List<User> assignedUsers) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignedUsers = assignedUsers;
        this.createdBy = createdBy;
    }

    public Task(UUID id, String title, String description, TaskStatus status, String createdBy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdBy = createdBy;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<User> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }
}

