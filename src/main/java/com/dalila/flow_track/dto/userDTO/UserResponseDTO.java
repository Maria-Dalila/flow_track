package com.dalila.flow_track.dto.userDTO;

import com.dalila.flow_track.model.task.Task;
import com.dalila.flow_track.model.user.UserRole;

import java.util.List;
import java.util.UUID;

public record UserResponseDTO(UUID id, String name, UserRole role) {
}
