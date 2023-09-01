package com.dalila.flow_track.dto.request;

import com.dalila.flow_track.dto.response.RegisteredUserResponse;
import com.dalila.flow_track.model.task.Task;
import com.dalila.flow_track.model.task.TaskStatus;
import com.dalila.flow_track.model.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.awt.font.FontRenderContext;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AssignedTaskRequest {

    @JsonInclude()
    private UUID id;
    @JsonInclude
    private String title;
    @JsonInclude
    private String description;
    private List<RegisteredUserResponse> assignedUsers;

    public AssignedTaskRequest(){}

    public AssignedTaskRequest(UUID id, String title, String description, List<RegisteredUserResponse> assignedUsers) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.assignedUsers = assignedUsers;
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

    public List<RegisteredUserResponse> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<RegisteredUserResponse> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public Task convertToEntity(AssignedTaskRequest assignedTask){
        List<User> users = assignedTask.getAssignedUsers().stream().map(user -> new User(UUID.fromString(user.getId()))).collect(Collectors.toList());
        Task task = new Task();
               task.setId(assignedTask.getId());
               task.setTitle(assignedTask.title);
               task.setDescription(assignedTask.description);
               task.getAssignedUsers().addAll(users);
        return task;
    }
}
