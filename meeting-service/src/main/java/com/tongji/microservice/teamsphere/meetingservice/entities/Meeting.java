package com.tongji.microservice.teamsphere.meetingservice.entities;

import java.time.LocalDateTime;

public class Meeting {
    public int id;
    public String projectId;
    public String title;
    public String description;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
}
