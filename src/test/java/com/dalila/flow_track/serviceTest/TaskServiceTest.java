package com.dalila.flow_track.serviceTest;


import com.dalila.flow_track.service.task.TaskServiceImpl;
import com.dalila.flow_track.service.user.UserServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TaskServiceTest {

    @Autowired
    public UserServiceImpl userService;

    @Autowired
    public TaskServiceImpl taskService;


    @Test
    public void shouldAddNewTaskInDatabase(){
    }

    @Test
    public void shouldReturnUUIDIdValid(){

    }

    @Test
    public void shouldReturnAListPopulated(){

    }


    @Test
    public void shouldReturnTasksAssignmentForEspecificalUser(){

    }

    @Test
    public void shouldReturnTasksAssignmentForEspecificalUserForKeyword(){

    }

    @Test
    public void shouldCreateTaskAssignedAndAddInListOfTaskInClassUser(){

    }
}
