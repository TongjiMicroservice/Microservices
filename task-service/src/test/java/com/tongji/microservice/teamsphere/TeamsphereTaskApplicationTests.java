package com.tongji.microservice.teamsphere;

import com.tongji.microservice.teamsphere.dto.taskservice.TaskMemberData;
import com.tongji.microservice.teamsphere.taskservice.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TeamsphereTaskApplication.class)
class TeamsphereTaskApplicationTests {

    @Autowired
    private TaskServiceImpl taskService;


    @ParameterizedTest
    @CsvFileSource(resources = "/testGetTaskInfo.csv", numLinesToSkip = 1)
    void testGetTaskInfo(int taskID, String expected) {
        var taskData=taskService.getTaskInfo(taskID).getTaskData();
        if (expected == null || expected.equals("null")) {
            assertNull(taskData);
        }else{
            assertEquals(expected, Integer.toString(taskData.getId()));
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/testGetTaskMember.csv", numLinesToSkip = 1)
    void testGetTaskMember(int taskID, String expected) {
        int[] expectedArray;
        if ("EMPTY_LIST".equals(expected)) {
            assertArrayEquals(new int[0], taskService.getTaskMember(taskID).getTaskMember().stream().map(TaskMemberData::getId).mapToInt(Integer::intValue).toArray());
        }else if (expected == null || expected.equals("null")) {
            assertNull(taskService.getTaskMember(taskID).getTaskMember());
        }
        else {
            expectedArray = Arrays.stream(expected.split(";"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int[] result = taskService.getTaskMember(taskID).getTaskMember().stream().map(TaskMemberData::getId).mapToInt(Integer::intValue).toArray();
            Arrays.sort(result);
            Arrays.sort(expectedArray);
            assertArrayEquals(expectedArray, result);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/testCreateTask.csv", numLinesToSkip = 1)
    void testCreateTask(String name, String description, int projectId, String deadline, int leader, int priority, String expected) {
        if(name.equals("EMPTY")){
            name="";
        }
        if(description.equals("EMPTY")){
            description="";
        }
        if (description.equals("EXCEED_VARCHAR(4096)")){
            description= "a".repeat(4097);
        }
        var response = taskService.createTask(name, description, projectId, LocalDateTime.parse(deadline) , leader, priority);
        assertEquals(Integer.parseInt(expected), response.getCode());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/testAddTaskMember.csv",numLinesToSkip = 1)
    void testAddTaskMember(int taskID,int memberID,int expected){
        var respCode=taskService.addTaskMember(taskID,memberID).getCode();
        assertEquals(expected,respCode);
    }
}
