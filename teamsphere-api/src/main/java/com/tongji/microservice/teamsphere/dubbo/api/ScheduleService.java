package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.entities.scheduleservice.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {
    void addEventToUserSchedule(String userId, LocalDateTime startTime, LocalDateTime endTime, String description);
    void removeEventFromUserSchedule(String userId, String eventId);
    List<Event> getUserSchedule(String userId);
}
