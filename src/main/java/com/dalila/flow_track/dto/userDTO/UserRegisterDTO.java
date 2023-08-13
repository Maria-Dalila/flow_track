package com.dalila.flow_track.dto.userDTO;

import com.dalila.flow_track.model.user.UserRole;

import java.util.UUID;

public record UserRegisterDTO(UUID id, String name, String login, String password, UserRole role) {
}
