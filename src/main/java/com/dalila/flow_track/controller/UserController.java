package com.dalila.flow_track.controller;

import com.dalila.flow_track.dto.request.UserAuthenticationRequest;
import com.dalila.flow_track.dto.request.UserRegisterRequest;
import com.dalila.flow_track.dto.request.UserUpdateRequest;
import com.dalila.flow_track.dto.response.LoginResponse;
import com.dalila.flow_track.dto.response.RegisteredUserResponse;
import com.dalila.flow_track.infra.security.TokenService;
import com.dalila.flow_track.model.user.User;
import com.dalila.flow_track.model.user.UserRole;
import com.dalila.flow_track.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public TokenService tokenService;

    @Autowired
    public UserServiceImpl userService;

    @Autowired
    public AuthenticationManager authenticationManager;
  
    @PostMapping("/login")
    public ResponseEntity login (@RequestBody UserAuthenticationRequest data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity registerANewUser(@RequestBody UserRegisterRequest data){
        if(userService.findByLogin(data.getLogin()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword  = new BCryptPasswordEncoder().encode(data.getPassword());
        User user = new User(null, data.getName(), data.getLogin(), encryptedPassword, UserRole.valueOf(data.getRole()));

        RegisteredUserResponse response = new RegisteredUserResponse();
        return ResponseEntity.ok(response.convertToResponse(userService.registerUser(user)));
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
