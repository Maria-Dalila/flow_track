package com.dalila.flow_track.controller;

import com.dalila.flow_track.dto.request.AssignedTaskRequest;
import com.dalila.flow_track.dto.request.PersonalTaskRequest;
import com.dalila.flow_track.dto.request.UpdateStatusTaskRequest;
import com.dalila.flow_track.dto.response.TaskResponse;

import com.dalila.flow_track.model.task.Task;

import com.dalila.flow_track.service.task.TaskServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    public TaskServiceImpl taskService;
    public AssignedTaskRequest taskAssignedToEntity = new AssignedTaskRequest();
    public PersonalTaskRequest taskPersonalToEntity = new PersonalTaskRequest();
    public TaskResponse convertToResponseSetup = new TaskResponse();

    public UpdateStatusTaskRequest convertStatusToEntity = new UpdateStatusTaskRequest();



    @PostMapping()/**TESTADO, FUNCIONANDO*/
    public TaskResponse createPersonalTask(@RequestBody PersonalTaskRequest personalTask){
        return convertToResponseSetup
                        .convertToResponse(taskService
                        .createPersonalTask(taskPersonalToEntity
                        .convertToEntity(personalTask)));
    }

    @PostMapping("/assign")/**TESTADO, FUNCIONANDO*/
    public ResponseEntity createAssignedTask(@RequestBody AssignedTaskRequest data){
        return ResponseEntity.ok(
                convertToResponseSetup
                        .convertToResponse(taskService.createTaskAssignment(taskAssignedToEntity
                                .convertToEntity(data))));
    }


    @PutMapping()/**TESTADO, FUNCIONANDO*/
    public ResponseEntity updateTask(@RequestBody PersonalTaskRequest data){
        return ResponseEntity.ok(
                convertToResponseSetup
                        .convertToResponse(taskService
                                .updateTask(taskPersonalToEntity
                                        .convertToEntity(data))));
    }

    @PutMapping("/addUsers")/**TESTADO, FUNCIONANDO*/
    public ResponseEntity addUsersToTask(@RequestBody AssignedTaskRequest assignedUsers){
        return ResponseEntity.ok(
                convertToResponseSetup
                        .convertToResponse(taskService
                                .addUsersToTask(taskAssignedToEntity
                                        .convertToEntity(assignedUsers))));

    }

    @PutMapping("/removeUsers")/**TESTADO, FUNCIONANDO*/
    public ResponseEntity removeUsersFromTask(@RequestBody AssignedTaskRequest assignedUsers){
        return ResponseEntity.ok(
                convertToResponseSetup
                        .convertToResponse(taskService
                                .removeUsersFromTask(taskAssignedToEntity
                                        .convertToEntity(assignedUsers))));

    }

    @PatchMapping()/**TESTADO, FUNCIONANDO*/
    public ResponseEntity updateStatusTask(@RequestBody UpdateStatusTaskRequest status) {
        return ResponseEntity.ok(convertToResponseSetup.convertToResponse( taskService.updateStatusTask(convertStatusToEntity.convertStatusToEntity(status))));
    }

    @GetMapping()/**TESTADO, FUNCIONANDO*/
    public List<TaskResponse> findAllPersonalTasks(){
        List<Task> tasks = taskService.findAllPersonalTasks();
        return tasks.stream().map(task -> convertToResponseSetup.convertToResponse(task)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")/**TESTADO, FUNCIONANDO*/
    public ResponseEntity findTaskById(@PathVariable String id){
        return ResponseEntity.ok(convertToResponseSetup.convertToResponse(taskService.findById(id)));
    }

    @GetMapping("/find/{keyword}")/**TESTADO, FUNCIONANDO*/
    public List<TaskResponse> findByKeyword(@PathVariable String keyword){
        List<Task> tasks=  taskService.findPersonalTaskByKeyword(keyword);
        return tasks.stream().map(
                        task -> convertToResponseSetup
                                .convertToResponse(task))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")/**TESTADO, FUNCIONANDO*/
    public ResponseEntity deleteTask(@PathVariable String id){
        boolean result = taskService.deletePersonalTask(id);
        if(result){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not allowed to delete this task.");
    }


    @GetMapping("/find")/**TESTADO, FUNCIONANDO*/
    public List<TaskResponse> findAllTasksAdmin(){
        List<Task> tasks = taskService.findAllTasks();
        return tasks.stream().map(task -> convertToResponseSetup.convertToResponse(task)).collect(Collectors.toList());
    }

    @GetMapping("/user/{id}")/**TESTADO, FUNCIONANDO*/
    public List<TaskResponse> findTasksForUser(@PathVariable String id){
        List <Task> tasks = taskService.findTasksForUser(UUID.fromString(id));
        return tasks.stream().map(task -> convertToResponseSetup.convertToResponse(task)).collect(Collectors.toList());
    }

    @GetMapping("/all/{keyword}")/**TESTADO, FUNCIONANDO*/
    public List<TaskResponse> findAllTasksForKeyword(@PathVariable String keyword){
        List <Task> tasks = taskService.findByKeyword(keyword);
        return tasks.stream().map(task -> convertToResponseSetup.convertToResponse(task)).collect(Collectors.toList());
    }

    @GetMapping("/user/{id}/{keyword}")/**TESTADO, FUNCIONANDO*/
    public List<TaskResponse> findTasksByKeywordForUser(@PathVariable String id, @PathVariable String keyword){
        List <Task> tasks = taskService.findTasksByKeywordAndUser(UUID.fromString(id), keyword);
        return tasks.stream().map(task -> convertToResponseSetup.convertToResponse(task)).collect(Collectors.toList());
    }

    @GetMapping("/lixeira")//ESSE ENDPOINT É PARA REALIZAÇÃO TESTE, PODE SER AJUSTADO POSTERIORMENTE PARA A CRIAÇÃO DE TAREFAS PUBLICAS.
    public List<TaskResponse> findTasksWithoutAssignedUsers(){
        List <Task> tasks = taskService.findTasksWithoutAssignedUsers();
        return tasks.stream().map(task -> convertToResponseSetup.convertToResponse(task)).collect(Collectors.toList());

    }



}
