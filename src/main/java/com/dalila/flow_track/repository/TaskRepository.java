package com.dalila.flow_track.repository;

import com.dalila.flow_track.model.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository  extends JpaRepository<Task, UUID> {
}
