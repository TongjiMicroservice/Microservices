package com.tongji.microservice.teamsphere.entities.taskservice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskMemberData {
    private int id;
    private int taskId;
    private int score;
    private LocalDateTime finishTime;
    private String fileURL;
}
