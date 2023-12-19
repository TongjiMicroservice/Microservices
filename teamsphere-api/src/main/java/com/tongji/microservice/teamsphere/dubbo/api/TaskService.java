package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.entities.taskservice.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {

    String createTask(String projectId, String title, String description, LocalDateTime deadline);
    void updateTaskMember(String taskId, String memberId);
    void updateTaskInfo(String taskId);
    List<Task> getTasksForProject(String projectId);
}
