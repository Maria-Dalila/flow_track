package com.dalila.flow_track.dto.request;

import com.dalila.flow_track.model.task.Task;
import com.dalila.flow_track.model.task.TaskStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;



public class PersonalTaskRequest {

    @JsonInclude
    private UUID id;
    private String title;
    private String description;

    public PersonalTaskRequest(){

    }

    public PersonalTaskRequest(UUID id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
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

    public Task convertToEntity(PersonalTaskRequest personalTask){
        Task task = new Task();
        task.setId(personalTask.getId());
        task.setTitle(personalTask.getTitle());
        task.setDescription(personalTask.getDescription());

        return task;
    }
}
