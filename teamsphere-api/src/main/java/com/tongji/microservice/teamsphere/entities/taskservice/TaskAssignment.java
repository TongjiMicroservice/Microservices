package com.tongji.microservice.teamsphere.entities.taskservice;

import lombok.Data;

@Data
public class TaskAssignment {
    private Long id;
    private Long taskId;
    private Long assignedMemberId;
}
