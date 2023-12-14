package com.tongji.microservice.teamsphere.entities.taskservice;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Task {
    private Long taskId;

    private Long projectId;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private String status;
}
