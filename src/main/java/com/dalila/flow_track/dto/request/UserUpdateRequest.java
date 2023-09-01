package com.dalila.flow_track.dto.request;

import com.dalila.flow_track.model.user.User;
import com.dalila.flow_track.model.user.UserRole;

import java.util.UUID;

public class UserUpdateRequest {

    private UUID id;
    private String name;
    private String login;
    private String password;
    private String role;

    public UserUpdateRequest(UUID id, String name, String login, String password, String role) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User convertToEntity(UserUpdateRequest userUpdated){
        return new User(
                userUpdated.getId(),
                userUpdated.getName(),
                userUpdated.getLogin(),
                userUpdated.getPassword(),
                UserRole.valueOf(userUpdated.getRole())
        );
    }
}


