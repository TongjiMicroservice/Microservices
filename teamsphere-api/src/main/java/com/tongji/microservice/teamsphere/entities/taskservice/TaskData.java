package com.tongji.microservice.teamsphere.entities.taskservice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskData {
    private int taskId;
    private int projectId;
    private int leader;
    private String name;
    private String description;
    private LocalDateTime deadline;
    private int status;
}
