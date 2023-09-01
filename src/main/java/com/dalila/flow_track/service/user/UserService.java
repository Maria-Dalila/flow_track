package com.dalila.flow_track.service.user;

import com.dalila.flow_track.model.user.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    public User registerUser(User user);
    public List<User> findUserByUsername(String username);
    public  User findUserById(String id);
    public User updateUser(User user);
    public List<User> findAll();
    public void deleteUser(UUID id);

}
