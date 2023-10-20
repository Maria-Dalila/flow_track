package com.dalila.flow_track.service.task;


import com.dalila.flow_track.model.task.Task;
import com.dalila.flow_track.model.task.TaskStatus;
import com.dalila.flow_track.model.user.User;
import com.dalila.flow_track.repository.TaskRepository;
import com.dalila.flow_track.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;
import java.util.UUID;


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    public TaskRepository taskRepository;
    @Autowired
    public UserRepository userRepository;

    public User getRequestingUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userRepository.findByLogin(authentication.getName());

        return user;
    }


    @Transactional
    @Override
    public Task createPersonalTask(Task taskPersonal) {
        User userRequest = getRequestingUser();

        taskPersonal.setId(null);
        taskPersonal.setStatus(TaskStatus.PENDING);
        taskPersonal.setCreatedBy(userRequest.getName());
        taskPersonal.getAssignedUsers().add(userRequest);
        Task taskSaved = taskRepository.save(taskPersonal);

        userRequest.getTasks().add(taskSaved);
        userRepository.save(userRequest);

        return taskSaved;
    }

    @Transactional
    @Override
    public Task createTaskAssignment(Task task) {
        User userRequest = getRequestingUser();


        Task taskPersistence = new Task(null, task.getTitle(),task.getDescription(), TaskStatus.PENDING, userRequest.getName());

        List<User> users = new ArrayList<>();
        for(User assigned: task.getAssignedUsers()) {
            User userPersistence = userRepository.findById(assigned.getId()).get();
            users.add(userPersistence);
            taskPersistence.getAssignedUsers().add(userPersistence);
        }
        Task taskSaved = taskRepository.save(taskPersistence);
        for(User assigned : users){
            assigned.getTasks().add(taskSaved);
        }
        return taskSaved;
    }

    @Transactional
    @Override
    public Task updateTask(Task taskUpdated) {
        User userRequest = getRequestingUser();

        Task task = taskRepository.findById(taskUpdated.getId()).get();

        if (task == null){
                throw new RuntimeException("this task does not exists");
        }
        else if(userRequest.getName().equals(task.getCreatedBy())){
                task.setTitle(taskUpdated.getTitle());
                task.setDescription(taskUpdated.getDescription());
                return  taskRepository.save(task);
        }
        else{
                throw new RuntimeException("User Unauthorized!");
        }
    }

    @Transactional
    @Override
    public Task addUsersToTask(Task newAssignedUsers){
        User userRequest = getRequestingUser();
        Task task = taskRepository.findById(newAssignedUsers.getId()).get();

        if(userRequest.getRole().toString().equals("ADMIN")){
            task.setCreatedBy(userRequest.getName());
            for(User u : newAssignedUsers.getAssignedUsers()) {
                User user = userRepository.findById(u.getId()).get();
                task.getAssignedUsers().add(user);
                user.getTasks().add(task);
                userRepository.save(user);
            }
            return taskRepository.save(task);
        }
        else throw new RuntimeException("This user doesn't have authorization to assign this task to other users.");
    }

    @Transactional
    @Override
    public Task removeUsersFromTask(Task removeAssignedUsers){
        User userRequest = getRequestingUser();
        Task task = taskRepository.findById(removeAssignedUsers.getId()).get();

        if(userRequest.getRole().toString().equals("ADMIN")){
            task.setCreatedBy(userRequest.getName());
            for(User u : removeAssignedUsers.getAssignedUsers()) {
                User user = userRepository.findById(u.getId()).get();
                task.getAssignedUsers().remove(user);
                user.getTasks().remove(task);
                userRepository.save(user);
            }
            return taskRepository.save(task);
        }
        else throw new RuntimeException("This user doesn't have authorization to remove other users from this task.");

    }

    @Transactional
    @Override
    public Task updateStatusTask(Task task) {
        User userRequest = getRequestingUser();

        Task taskStatusUpdated = taskRepository.findById(task.getId()).get();

        if(taskStatusUpdated.getAssignedUsers().contains(userRequest) || taskStatusUpdated.getCreatedBy() == userRequest.getName()){
            taskStatusUpdated.setStatus(task.getStatus());
            return taskRepository.save(taskStatusUpdated);
        }
        else {
            throw new RuntimeException("This user don't is authorized for update Status  ");
        }
    }

    @Transactional
    @Override
    public boolean deletePersonalTask(String id) {
        User userRequest = getRequestingUser();
        UUID taskId  = UUID.fromString(id);

        Task task = taskRepository.findById(taskId).get();

        if(task.getCreatedBy().equals(userRequest.getName()) && userRequest.getRole().toString().equals("ADMIN") || userRequest.getRole().toString().equals("ADMIN") && task.getAssignedUsers().isEmpty()){
            for(User u: task.getAssignedUsers()){
                User removeUser = userRepository.findById(u.getId()).get();
                removeUser.getTasks().remove(task);
                task.getAssignedUsers().remove(removeUser);
                userRepository.save(removeUser);
            }

            taskRepository.deleteById(task.getId());
            return true;
        }
        else if(task.getCreatedBy().equals(userRequest.getName()) && userRequest.getRole().toString().equals("USER")){
            task.getAssignedUsers().remove(userRequest);
            userRequest.getTasks().remove(task);
            userRepository.save(userRequest);
            taskRepository.deleteById(task.getId());
            return true;

        }
        return false;
    }

    @Override
    public List<Task> findAllPersonalTasks() {
        User userRequest = getRequestingUser();

        if(userRequest.getRole().toString().equals("USER")) {
            return userRequest.getTasks();
        }
        else {


            List<Task> tasksAdmin =  taskRepository.findByCreatedBy(userRequest.getName());
            return tasksAdmin;
        }
    }

    @Override
    public List<Task> findPersonalTaskByKeyword( String keyword) {
        User userRequest = getRequestingUser();

        List<Task> tasks = taskRepository.findByKeywordAndUser(userRequest.getId(), userRequest.getName(), keyword);
        return tasks;
    }

    @Override
    public Task findById(String id){
        return taskRepository.findById(UUID.fromString(id)).get();
    }

    @Override
    public List<Task> findAllTasks(){
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findTasksForUser(UUID userId){
        User user = userRepository.findById(userId).get();
        return user.getTasks();
    }

    @Override
    public List<Task> findByKeyword(String keyword){
        List<Task> tasks = taskRepository.findByKeywordInTitleOrDescription(keyword);
        return tasks;
    }

    @Override
    public List<Task> findTasksByKeywordAndUser(UUID userId, String keyword){
       User user = userRepository.findById(userId).get();
       List<Task> tasks = taskRepository.findByKeywordAndUser(user.getId(),user.getName(), keyword);
       return tasks;
    }

    public  List<Task> findTasksWithoutAssignedUsers(){
       return taskRepository.findTaskWithoutAssignedUser();
   }

   public void deleteAllTasksForUser(UUID userId) {
       User user = userRepository.findById(userId).get();

       for (Task task : user.getTasks()) {
            task.getAssignedUsers().remove(user);
            if(task.getAssignedUsers().isEmpty() && task.getCreatedBy().equals(user.getName())){
                taskRepository.deleteById(task.getId());
            }
            else{
                taskRepository.save(task);
            }
       }
   }

}
