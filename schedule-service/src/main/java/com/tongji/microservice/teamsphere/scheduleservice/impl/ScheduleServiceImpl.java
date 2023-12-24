package com.tongji.microservice.teamsphere.scheduleservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventIdResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventInfoResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventsResponse;
import com.tongji.microservice.teamsphere.dubbo.api.ScheduleService;
import com.tongji.microservice.teamsphere.scheduleservice.entities.Event;
import com.tongji.microservice.teamsphere.scheduleservice.mapper.EventMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDateTime;

import static com.tongji.microservice.teamsphere.dto.APIResponse.fail;

@DubboService
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private EventMapper eventMapper;

    @Override
    public EventIdResponse createEvent(int userId, LocalDateTime startTime, LocalDateTime deadline, String description, String title, int priority) {
        APIResponse apiResponse = null;
        int scheduleid = -1;
        if(userId<0){
            apiResponse = APIResponse.fakeToken();
        }
        // 现在认为调用该函数时userId一定是正确的

        // priority必须是0-2之间，否则报错
        else if(priority<0||priority>2){
            apiResponse = APIResponse.fail("Unexpected Priority");
        }
        else{
            scheduleid = eventMapper.getMaxId()+1;
            Event event = new Event();
            event.id=scheduleid;

        }

        EventIdResponse eventIdResponse;
        if(scheduleid>=0){
            eventIdResponse = new EventIdResponse(apiResponse,scheduleid);
        }
        else {
            eventIdResponse = new EventIdResponse(apiResponse);
        }
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
