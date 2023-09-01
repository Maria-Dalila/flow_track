package com.dalila.flow_track.dto.request;

import com.dalila.flow_track.model.task.Task;
import com.dalila.flow_track.model.task.TaskStatus;

import java.util.UUID;

public class UpdateStatusTaskRequest {

    private UUID id;
    private String status;

    public UpdateStatusTaskRequest(){}

    public UpdateStatusTaskRequest(UUID id, String status) {
        this.id = id;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Task convertStatusToEntity(UpdateStatusTaskRequest statusTask){
        Task task = new Task();
        task.setId(statusTask.getId());
        task.setStatus(TaskStatus.valueOf(statusTask.getStatus()));
        return task;
    }

}
