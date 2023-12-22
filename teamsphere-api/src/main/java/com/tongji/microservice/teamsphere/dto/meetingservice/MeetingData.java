package com.tongji.microservice.teamsphere.dto.meetingservice;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MeetingData implements Serializable {
    public int id;
    public String projectId;
    public String title;
    public String description;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
}
