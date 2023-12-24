package com.tongji.microservice.teamsphere.taskservice.impl;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.ProjectTaskResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskMemberResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskResponse;
import com.tongji.microservice.teamsphere.dubbo.api.TaskService;
import com.tongji.microservice.teamsphere.entities.taskservice.TaskData;
import com.tongji.microservice.teamsphere.taskservice.mapper.TaskMapper;
import com.tongji.microservice.teamsphere.taskservice.mapper.TaskMemberMapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskServiceImpl implements TaskService {

    @DubboReference(check = false)
    private TaskService taskService;
    // 注入TaskMapper和TaskMemberMapper
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskMemberMapper memberMapper;
    @Override
    public APIResponse createTask(String token, int projectId, TaskData taskData) {
        return null;
    }

    @Override
    public APIResponse deleteTask(String token, int taskId) {
        return null;
    }

    @Override
    public APIResponse addTaskMember(String token, int taskId, int memberId) {
        return null;
    }

    @Override
    public APIResponse deleteTaskMember(String token, int taskId, int memberId) {
        return null;
    }

    @Override
    public APIResponse scoreTaskMember(String token, int taskId, int memberId, int score) {
        return null;
    }

    @Override
    public APIResponse uploadTaskFile(String token, int taskId, int memberId, String fileURL) {
        return null;
    }

    @Override
    public APIResponse updateTaskInfo(String token, int taskId, TaskData taskData) {
        return null;
    }

    @Override
    public TaskResponse getTaskInfo(String token, int taskId) {
        return null;
    }

    @Override
    public TaskMemberResponse getTaskMember(String token, int taskId) {
        return null;
    }

    @Override
    public ProjectTaskResponse getTasksForProject(String token, int projectId) {
        return null;
    }
}
