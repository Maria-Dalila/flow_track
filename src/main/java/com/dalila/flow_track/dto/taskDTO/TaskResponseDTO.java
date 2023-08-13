package com.dalila.flow_track.dto.taskDTO;

import com.dalila.flow_track.model.task.TaskStatus;
import com.dalila.flow_track.model.user.User;

import java.util.List;
import java.util.UUID;

public record TaskResponseDTO(UUID id, String title, String description, TaskStatus status, String createdBy, List<User> assignedUsers) {
}
