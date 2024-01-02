package com.tongji.microservice.teamsphere.dto.taskservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskMemberData implements Serializable {
    private int id;
    private int taskId;
    private int score;
}
