package com.dalila.flow_track.service;

import com.dalila.flow_track.dto.userDTO.UserRegisterDTO;
import com.dalila.flow_track.dto.userDTO.UserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserResponseDTO registerUser(UserRegisterDTO user) {
        return null;
    }

    @Override
    public List<UserResponseDTO> findUserByUsername(String username) {
        return null;
    }

    @Override
    public UserResponseDTO updateUser(UserRegisterDTO user) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}

