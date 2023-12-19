package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.entities.scheduleservice.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {
    void createEvent(String userId, LocalDateTime startTime, LocalDateTime endTime, String description);
    void removeEvent(String userId, String eventId);
    List<Event> getEvents(String userId);

    void getEventInfo();

    void updateEventInfo();
}
