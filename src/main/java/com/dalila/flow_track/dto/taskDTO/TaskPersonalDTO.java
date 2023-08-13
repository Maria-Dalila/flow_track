package com.dalila.flow_track.dto.taskDTO;

import com.dalila.flow_track.model.task.TaskStatus;

import java.util.UUID;

public record TaskPersonalDTO(UUID id, String title, String description) {
}
