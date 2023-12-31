package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventIdResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventInfoResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventsResponse;

import java.time.LocalDateTime;

public interface    ScheduleService {
    EventIdResponse createEvent(int userId, LocalDateTime startTime, LocalDateTime deadline, String description, String title, int priority);
    APIResponse removeEvent(int eventId);
    EventsResponse getEvents(int userId);

    EventInfoResponse getEventInfo(int eventId);

    EventIdResponse getEventId(int userId, String title);

    APIResponse updateEventInfo(int eventId,LocalDateTime startTime, LocalDateTime deadline, String description, String title, int priority);
}
