package com.dalila.flow_track.service.user;


import com.dalila.flow_track.model.user.User;
import com.dalila.flow_track.repository.UserRepository;
import com.dalila.flow_track.service.task.TaskServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskServiceImpl taskService;

    @Override
    public User registerUser(User user) {
        User existsUser = userRepository.findByName(user.getName());
        if(existsUser != null){
            throw new RuntimeException("Username invalid! This username is already in use, please choose a different username.");
        }
        else {
            return userRepository.save(user);
        }
    }

    @Override
    public List<User> findUserByUsername(String username) {
        List<User> users = userRepository.findAllByNameContaining(username);
        return users;
    }

    @Override
    public User findUserById(String id) {
        return userRepository.findById(UUID.fromString(id)).get();
    }

    @Override
    public User updateUser(User userUpdated) {
        Optional<User> idValidExists = userRepository.findById(userUpdated.getId());
        if(idValidExists.isEmpty()){
            throw new RuntimeException("This User don't is register.");
        }
        else {
            User user = idValidExists.get();
            userUpdated.setName(user.getName());
            return userRepository.save(userUpdated);
        }
    }

    public List<User> findAll(){
        List<User> users = userRepository.findAll();
            return users;
        }

    @Override
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id).get();
        taskService.deleteAllTasksForUser(user.getId());
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }

    public UserDetails findByLogin(String login){
        return userRepository.findByLogin(login);
    }
}

