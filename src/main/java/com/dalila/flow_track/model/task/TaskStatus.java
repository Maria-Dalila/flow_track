package com.dalila.flow_track.model.task;

public enum TaskStatus {

    PENDING("pending"),
    STARTED("started"),
    COMPLETED("completed");

    private String status;

    TaskStatus(String status){
         this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
