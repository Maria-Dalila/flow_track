package com.dalila.flow_track.service.task;

import com.dalila.flow_track.model.task.Task;
import com.dalila.flow_track.model.user.User;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    public Task createPersonalTask(Task taskPersonal);

    public List<Task> findAllPersonalTasks();

    public Task updateTask(Task task);

    public boolean deletePersonalTask(String taskId);

    public Task updateStatusTask(Task status);

    public List<Task> findPersonalTaskByKeyword( String keyword );

    public Task findById(String id);

    public Task createTaskAssignment(Task taskAssignment);

    public List<Task> findAllTasks();

    public List<Task> findTasksForUser(UUID userId);

    public List<Task> findByKeyword(String keyword);

    public List<Task> findTasksByKeywordAndUser(UUID userId, String keyword);

    public Task addUsersToTask(Task newAssignedUsers);

    public Task removeUsersFromTask(Task removeAssignedUsers);

}
