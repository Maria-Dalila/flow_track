package com.dalila.flow_track.controller;

import com.dalila.flow_track.dto.request.UserRegisterRequest;
import com.dalila.flow_track.dto.request.UserUpdateRequest;
import com.dalila.flow_track.dto.response.RegisteredUserResponse;
import com.dalila.flow_track.model.user.User;
import com.dalila.flow_track.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserServiceImpl userService;



    @PostMapping()//TESTADO, FUNDIONANDO
    public ResponseEntity registerANewUser(@RequestBody UserRegisterRequest data){
        RegisteredUserResponse response = new RegisteredUserResponse();
        return ResponseEntity.ok(response.convertToResponse(userService.registerUser(data.convertToEntity(data))));
    }

    @GetMapping("find/{id}")//TESTADO, FUNDIONANDO
    public ResponseEntity findById(@PathVariable String id){

        RegisteredUserResponse response = new RegisteredUserResponse();

        return ResponseEntity.ok(response.convertToResponse(userService.findUserById(id)));
    }

    @GetMapping()//TESTADO, FUNDIONANDO
    public List<RegisteredUserResponse> findAll(){

        RegisteredUserResponse response = new RegisteredUserResponse();

        List<User> users = userService.findAll();
        List<RegisteredUserResponse> allUsers = users.stream().map(response::convertToResponse).collect(Collectors.toList());

        return allUsers;
    }


    @PutMapping()//TESTADO, FUNDIONANDO
    public ResponseEntity updateUser(@RequestBody UserUpdateRequest data){
        RegisteredUserResponse response = new RegisteredUserResponse();
        return ResponseEntity.ok(response.convertToResponse(userService.updateUser(data.convertToEntity(data))));
    }

    @DeleteMapping("/{id}")//TESTADO, FUNDIONANDO
    public ResponseEntity deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{keyword}")//TESTADO, FUNDIONANDO
    public List<RegisteredUserResponse> searchUserForKeyword(@PathVariable String keyword){
        RegisteredUserResponse response = new RegisteredUserResponse();
        List<User> users =  userService.findUserByUsername(keyword);
        List<RegisteredUserResponse> allUsers = users.stream().map(response::convertToResponse).collect(Collectors.toList());
        return allUsers;
    }


}
