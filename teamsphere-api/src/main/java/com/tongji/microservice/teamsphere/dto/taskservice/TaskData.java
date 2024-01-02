package com.tongji.microservice.teamsphere.dto.taskservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskData implements Serializable {
    private int id;
    private int projectId;
    private int leader;
    private String name;
    private String description;
    private LocalDateTime deadline;
    private int status;
    private int priority;
    private String file;
    private LocalDateTime finishTime;
    private String leaderName;
    private String fileName;
}
