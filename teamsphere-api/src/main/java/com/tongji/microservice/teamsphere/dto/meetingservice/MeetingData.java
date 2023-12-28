package com.tongji.microservice.teamsphere.dto.meetingservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingData implements Serializable {
    public int id;
    public String projectId;
    public String title;
    public String description;
    public LocalDateTime startTime;
    public int duration;
    public String url;
    public String bookId;
}
