package com.tongji.microservice.teamsphere.scheduleservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventData;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventIdResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventInfoResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventsResponse;
import com.tongji.microservice.teamsphere.dubbo.api.ScheduleService;
import com.tongji.microservice.teamsphere.scheduleservice.entities.Event;
import com.tongji.microservice.teamsphere.scheduleservice.mapper.EventMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.tongji.microservice.teamsphere.dto.APIResponse.fail;

@DubboService
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private EventMapper eventMapper;

    private boolean CheckTimeCorruptByUserId(int userId, LocalDateTime startTime) {
        try {
            int reslt = eventMapper.getCountOfEvents(userId);
            if (reslt > 0) return true;
            else return false;
        } catch (Exception e) {
            System.out.println("数据库查询过程中发生异常：");
            e.printStackTrace(); // 打印异常堆栈跟踪
            return false; // 或者根据你的业务逻辑返回相应的值
        }

    }

    @Override
    public EventIdResponse createEvent(int userId, LocalDateTime startTime, LocalDateTime deadline, String description, String title, int priority) {
        APIResponse apiResponse = null;
        int scheduleid = -1;
        if (userId < 0) {
            apiResponse = APIResponse.fakeToken();
        }
        // 现在认为调用该函数时userId一定是正确的

        // priority必须是0-2之间，否则报错
        else if (priority < 0 || priority > 2) {
            apiResponse = APIResponse.fail("Unexpected Priority");
        }
        else if(CheckTimeCorruptByUserId(userId,startTime)){
            apiResponse = APIResponse.fail("插入时间存在冲突");
        }
        else {
            scheduleid = eventMapper.getMaxId() + 1;
            Event event = new Event();
            event.id = scheduleid;
            event.userId = userId;
            event.startTime = startTime;
            event.deadline = deadline;
            event.title = title;
            event.description = description;
            event.priority = priority;
            var flag = eventMapper.insert(event);
            if (flag == 0) {
                apiResponse = APIResponse.fail("Unable to insert into database");
                scheduleid = -1;
            } else {
                apiResponse = APIResponse.success();
            }
        }

        EventIdResponse eventIdResponse;
        if (scheduleid >= 0) {
            eventIdResponse = new EventIdResponse(apiResponse, scheduleid);
        } else {
            eventIdResponse = new EventIdResponse(apiResponse);
        }
        return eventIdResponse;
    }

    @Override
    public EventIdResponse getEventId(int userId, String title) {
        int eventid = -1;
        APIResponse apiResponse = null;
        EventIdResponse eventIdResponse;
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        if (userId < 0) {
            String tmp = String.format("Unexpected Userid:%d", userId);
            apiResponse = APIResponse.fail(tmp);
            return new EventIdResponse(apiResponse);
        }
        queryWrapper.eq("user_Id", userId)
                .eq("title", title);
        Event event = eventMapper.selectOne(queryWrapper);
        if (event == null) {
            apiResponse = APIResponse.fail("没有符合要求的事件");
        } else if (event.id < 0) {
            apiResponse = APIResponse.fail("错误的会议号");
        } else {
            eventid = event.id;
        }


        if (eventid >= 0) {
            eventIdResponse = new EventIdResponse(apiResponse, eventid);
        } else {
            eventIdResponse = new EventIdResponse(apiResponse);
        }
        return eventIdResponse;
    }

    @Override
    public APIResponse removeEvent(int eventId) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", eventId);
        Event event = eventMapper.selectOne(queryWrapper);
        if (event == null) {
            return APIResponse.fail("No such event");
        } else {
            int flag = eventMapper.delete(queryWrapper);
            if (flag == 0) {
                return APIResponse.fail("Unexpected error,unable to remove it from database");
            }
            return APIResponse.success();
        }
    }

    @Override
    public APIResponse updateEventInfo(int eventId, LocalDateTime startTime, LocalDateTime deadline, String description, String title, int priority) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", eventId);
        Event event = eventMapper.selectOne(queryWrapper);
        if (event == null) {
            return APIResponse.fail("No such event");
        } else if (priority < 0 || priority > 2) {
            return APIResponse.fail("Unexpected priority");
        }
        else {
            int userId = eventMapper.getUserIdByEventId(eventId );
            if(CheckTimeCorruptByUserId(userId,startTime))
                return APIResponse.fail("存在时间冲突");
        }

        UpdateWrapper<Event> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", eventId);
        if (startTime != null)
            updateWrapper.set("start_time", startTime);
        if (deadline != null)
            updateWrapper.set("deadline", deadline);
        if (description != null)
            updateWrapper.set("description", description);
        if (title != null)
            updateWrapper.set("title", title);

        updateWrapper.set("priority", priority);

        int flag = eventMapper.update(updateWrapper);
        if (flag == 0) {
            return APIResponse.fail("Unexpected error,unable to update database");
        }
        return APIResponse.success();
    }

    //
    @Override
    public EventInfoResponse getEventInfo(int eventId) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", eventId);
        Event event = eventMapper.selectOne(queryWrapper);
        APIResponse apiResponse;
        if (event == null) {
            apiResponse = APIResponse.fail("No such event");
            return new EventInfoResponse(apiResponse);
        } else {
            apiResponse = APIResponse.success();
            return new EventInfoResponse(apiResponse, event.userId, event.startTime, event.deadline, event.description, event.title, event.priority);

        }

    }

    @Override
    public EventsResponse getEvents(int userId) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Event> eventList = eventMapper.selectList(queryWrapper);

        List<EventData> eventDataList = new ArrayList<>();
        for (Event event : eventList) {
            EventData eventData = new EventData(event.title, event.description, event.startTime, event.deadline, event.priority);
            eventDataList.add(eventData);

        }
        return new EventsResponse(APIResponse.success(), eventDataList);
    }

}
