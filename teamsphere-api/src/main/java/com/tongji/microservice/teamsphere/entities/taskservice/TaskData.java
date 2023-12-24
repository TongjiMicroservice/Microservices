package com.tongji.microservice.teamsphere.entities.taskservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskData implements Serializable {
    private int taskId;
    private int projectId;
    private int leader;
    private String name;
    private String description;
    private LocalDateTime deadline;
    private int status;
}
