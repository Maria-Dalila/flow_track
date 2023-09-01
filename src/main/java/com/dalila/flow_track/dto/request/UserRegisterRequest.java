package com.dalila.flow_track.dto.request;

import com.dalila.flow_track.model.user.User;
import com.dalila.flow_track.model.user.UserRole;

public class UserRegisterRequest {

    private String name;
    private String login;
    private String password;
    private String role;

    public UserRegisterRequest(String name, String login, String password, String role) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
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

    public User convertToEntity(UserRegisterRequest userRegister){
        return new User(
                null,
                userRegister.name,
                userRegister.getLogin(),
                userRegister.password,
                UserRole.valueOf(userRegister.role)
        );
    }
}
