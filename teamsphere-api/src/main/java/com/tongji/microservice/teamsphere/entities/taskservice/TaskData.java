package com.tongji.microservice.teamsphere.entities.taskservice;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskData {
    private int taskId;
    private int projectId;
    private int leader;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private String status;
}
