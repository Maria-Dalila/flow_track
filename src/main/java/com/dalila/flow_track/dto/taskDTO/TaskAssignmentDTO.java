package com.dalila.flow_track.dto.taskDTO;

import com.dalila.flow_track.model.task.TaskStatus;
import com.dalila.flow_track.model.user.User;

import java.util.List;
import java.util.UUID;

public record TaskAssignmentDTO(UUID id, String title, String description, List<User> assignedUsers) {
}
