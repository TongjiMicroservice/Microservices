package com.tongji.microservice.teamsphere.entities.meetingservice;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Meeting {
    private Long meetingId;

    private Long projectId;
    private LocalDateTime meetingTime;
    private String agenda;
}
