package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.CreateTaskResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskMemberResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.ProjectTaskResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskResponse;
import com.tongji.microservice.teamsphere.entities.taskservice.TaskData;

import java.time.LocalDateTime;

public interface TaskService {

    CreateTaskResponse createTask(String name, String description, int projectId, LocalDateTime deadline , int leader, int priority);
    APIResponse deleteTask(int taskId);

    APIResponse addTaskMember(int taskId, int memberId);
    APIResponse deleteTaskMember(int taskId, int memberId);

    APIResponse scoreTaskMember(int taskId, int memberId, int score);

    APIResponse uploadTaskFile(int taskId, int memberId, String fileURL);
    APIResponse updateTaskInfo(int taskId, TaskData taskData);
    TaskResponse getTaskInfo(int taskId);

    TaskMemberResponse getTaskMember(int taskId);
    ProjectTaskResponse getTasksForProject(int projectId);
}
