package com.dalila.flow_track.dto.response;

import com.dalila.flow_track.model.task.Task;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TaskResponse {
    private String id;
    private String title;
    private String description;
    private String status;
    private String createdBy;
    private List<RegisteredUserResponse> assignedUsers;

    public TaskResponse(){}

    public TaskResponse(String id, String title, String description, String status, String createdBy, List<RegisteredUserResponse> assignedUsers) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdBy = createdBy;
        this.assignedUsers = assignedUsers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<RegisteredUserResponse> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<RegisteredUserResponse> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public TaskResponse convertToResponse(Task task){
        List<RegisteredUserResponse> users = task.getAssignedUsers().stream().map(user -> new RegisteredUserResponse(
                user.getId().toString(),
                user.getName(),
                user.getRole().toString()
        )).collect(Collectors.toList());

        return new TaskResponse(task.getId().toString(), task.getTitle(), task.getDescription(), task.getStatus().toString(), task.getCreatedBy(), users);
    }
}
