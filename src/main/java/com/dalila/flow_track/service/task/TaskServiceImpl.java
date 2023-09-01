package com.dalila.flow_track.service.task;


import com.dalila.flow_track.model.task.Task;
import com.dalila.flow_track.model.task.TaskStatus;
import com.dalila.flow_track.model.user.User;
import com.dalila.flow_track.repository.TaskRepository;
import com.dalila.flow_track.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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


    @Transactional
    @Override
    public Task createPersonalTask(Task taskPersonal) {
        UUID id  = UUID.fromString("fd217c13-516e-48b8-b44e-c704c5815cbe");
        User user = userRepository.findById(id).get();//temp: before implementation of security
        taskPersonal.setId(null);
        taskPersonal.setStatus(TaskStatus.PENDING);
        taskPersonal.setCreatedBy(user.getName());
        taskPersonal.getAssignedUsers().add(user);
        Task taskSaved = taskRepository.save(taskPersonal);

        user.getTasks().add(taskSaved);
        userRepository.save(user);

        return taskSaved;
    }

    @Transactional
    @Override
    public Task createTaskAssignment(Task task) {
        UUID userIdRequest  = UUID.fromString("18ceeb85-c275-4d88-be59-43d6616c0311");
        User user = userRepository.findById(userIdRequest).get();

        Task taskPersistence = new Task(null, task.getTitle(),task.getDescription(), TaskStatus.PENDING, user.getName());

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
        UUID userIdRequest = UUID.fromString("f81eff78-7e48-46b2-b723-5f9abce54097");
        User user = userRepository.findById(userIdRequest).get();

        Task task = taskRepository.findById(taskUpdated.getId()).get();

        if (task == null){
                throw new RuntimeException("this task does not exists");
        }
        else if(user.getName().equals(task.getCreatedBy())){
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
        UUID userIdRequest = UUID.fromString("18ceeb85-c275-4d88-be59-43d6616c0311");
        User userRequest = userRepository.findById(userIdRequest).get();
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
        UUID userIdRequest = UUID.fromString("18ceeb85-c275-4d88-be59-43d6616c0311");
        User userRequest = userRepository.findById(userIdRequest).get();
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
        UUID userIdRequest = UUID.fromString("c1ff176c-50c1-4e1b-b19b-ff368a21aec5");
        User user  = userRepository.findById(userIdRequest).get();

        Task taskStatusUpdated = taskRepository.findById(task.getId()).get();

        if(taskStatusUpdated.getAssignedUsers().contains(user) || taskStatusUpdated.getCreatedBy() == user.getName()){
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
        UUID userId  = UUID.fromString("18ceeb85-c275-4d88-be59-43d6616c0311");
        UUID taskId  = UUID.fromString(id);

        User user = userRepository.findById(userId).get();
        Task task = taskRepository.findById(taskId).get();

        if(task.getCreatedBy().equals(user.getName()) && user.getRole().toString().equals("ADMIN") || user.getRole().toString().equals("ADMIN") && task.getAssignedUsers().isEmpty()){
            for(User u: task.getAssignedUsers()){
                User removeUser = userRepository.findById(u.getId()).get();
                removeUser.getTasks().remove(task);
                task.getAssignedUsers().remove(removeUser);
                userRepository.save(removeUser);
            }

            taskRepository.deleteById(task.getId());
            return true;
        }
        else if(task.getCreatedBy().equals(user.getName()) && user.getRole().toString().equals("USER")){
            task.getAssignedUsers().remove(user);
            user.getTasks().remove(task);
            userRepository.save(user);
            taskRepository.deleteById(task.getId());
            return true;

        }return false;
    }

    @Override
    public List<Task> findAllPersonalTasks() {
        UUID userIdRequest = UUID.fromString("18ceeb85-c275-4d88-be59-43d6616c0311");
        User user = userRepository.findById(userIdRequest).get();

        if(user.getRole().toString().equals("USER")) {
            return user.getTasks();
        }
        else {
            List<Task> tasksAdmin = taskRepository.findByCreatedBy(user.getName());
            return tasksAdmin;
        }
    }

    @Override
    public List<Task> findPersonalTaskByKeyword( String keyword) {
        UUID id = UUID.fromString("18ceeb85-c275-4d88-be59-43d6616c0311");
        User userRequest = userRepository.findById(id).get();

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
