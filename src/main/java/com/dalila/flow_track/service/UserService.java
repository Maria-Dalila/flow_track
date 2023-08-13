package com.dalila.flow_track.service;


import com.dalila.flow_track.dto.userDTO.UserRegisterDTO;
import com.dalila.flow_track.dto.userDTO.UserResponseDTO;

import java.util.List;

public interface UserService {

    public UserResponseDTO registerUser(UserRegisterDTO user);
    public List<UserResponseDTO> findUserByUsername(String username);
    public UserResponseDTO updateUser(UserRegisterDTO user);
    public void deleteUser(Long id);

}
