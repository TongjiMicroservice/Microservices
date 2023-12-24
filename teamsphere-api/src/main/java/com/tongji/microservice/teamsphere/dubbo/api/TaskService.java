package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskMemberResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.ProjectTaskResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskResponse;
import com.tongji.microservice.teamsphere.entities.taskservice.TaskData;

public interface TaskService {

    APIResponse createTask(String token, int projectId, TaskData taskData);
    APIResponse deleteTask(String token, int taskId);

    APIResponse addTaskMember(String token, int taskId, int memberId);
    APIResponse deleteTaskMember(String token, int taskId, int memberId);

    APIResponse scoreTaskMember(String token, int taskId, int memberId, int score);

    APIResponse uploadTaskFile(String token, int taskId, int memberId, String fileURL);
    APIResponse updateTaskInfo(String token, int taskId, TaskData taskData);
    TaskResponse getTaskInfo(String token, int taskId);

    TaskMemberResponse getTaskMember(String token, int taskId);
    ProjectTaskResponse getTasksForProject(String token, int projectId);
}
