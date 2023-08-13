package com.dalila.flow_track.service;

import com.dalila.flow_track.dto.taskDTO.TaskAssignmentDTO;
import com.dalila.flow_track.dto.taskDTO.TaskPersonalDTO;
import com.dalila.flow_track.dto.taskDTO.TaskResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService{

    @Override
    public TaskResponseDTO createPersonalTask(TaskPersonalDTO taskPersonal) {
        return null;
    }

    @Override
    public List<TaskResponseDTO> findAllPersonalTasks() {
        return null;
    }

    @Override
    public TaskResponseDTO updatePersonalTask(TaskResponseDTO task) {
        return null;
    }

    @Override
    public void deletePersonalTask(UUID taskId) {

    }

    @Override
    public TaskResponseDTO updateStatusTask(String status) {
        return null;
    }

    @Override
    public List<TaskResponseDTO> findTaskByTitle(String title) {
        return null;
    }

    @Override
    public boolean createTaskAssignment(TaskAssignmentDTO taskAssignment) {
        return false;
    }
}
