package com.dalila.flow_track.dto.response;

import com.dalila.flow_track.model.user.User;

import java.util.UUID;

public class RegisteredUserResponse {

    private String id;
    private String name;
    private String role;

    public RegisteredUserResponse(){}

    public RegisteredUserResponse(String id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public RegisteredUserResponse convertToResponse(User user){
        return new RegisteredUserResponse(
                user.getId().toString(),
                user.getName(),
                user.getRole().toString()
        );
    }
}
