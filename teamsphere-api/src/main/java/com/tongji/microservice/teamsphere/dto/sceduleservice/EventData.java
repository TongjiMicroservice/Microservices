package com.tongji.microservice.teamsphere.dto.sceduleservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventData implements Serializable {
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime deadline;
    private int priority;

}
