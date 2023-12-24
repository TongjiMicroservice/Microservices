package com.tongji.microservice.teamsphere.scheduleservice.impl;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventIdResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventInfoResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventsResponse;
import com.tongji.microservice.teamsphere.dubbo.api.ScheduleService;
import com.tongji.microservice.teamsphere.scheduleservice.mapper.EventMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@DubboService
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private EventMapper eventMapper;

    @Override
    public EventIdResponse createEvent(int userId, LocalDateTime startTime, LocalDateTime deadline, String description, String title, int priority) {
        return null;
    }

    @Override
    public EventIdResponse getEventId(int userId, String title) {
        return null;
    }

    @Override
    public APIResponse removeEvent(int eventId) {
        return null;
    }

    @Override
    public APIResponse updateEventInfo(int eventId, LocalDateTime startTime, LocalDateTime deadline, String description, String title, int priority) {
        return null;
    }

    @Override
    public EventInfoResponse getEventInfo(int eventId) {
        return null;
    }

    @Override
    public EventsResponse getEvents(int userId) {
        return null;
    }

}
