package com.tongji.microservice.teamsphere.dubbo.api;

public interface TaskService {
    boolean createTask(String taskName, String taskDescription, String taskDeadline, String taskPriority, String taskTag, String taskStatus, String taskCreator, String taskAssignee);
    String helloTaskService();
}
