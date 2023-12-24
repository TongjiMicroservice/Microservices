package com.tongji.microservice.teamsphere.entities.taskservice;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskMember {
    private int id;
    private int taskId;
    private int score;
    private LocalDateTime finishTime;
    private String fileURL;
}
