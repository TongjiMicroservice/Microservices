package com.tongji.microservice.teamsphere.entities.scheduleservice;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {
    private Long scheduleId;
    private Long userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
}
