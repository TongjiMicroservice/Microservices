package com.tongji.microservice.teamsphere.gatewayservice.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventIdResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventInfoResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventsResponse;
import com.tongji.microservice.teamsphere.dubbo.api.ScheduleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@Tag(name= "ScheduleController", description = "日程微服务接口")
public class ScheduleController {
    @DubboReference(check = false)
    private ScheduleService scheduleService;

    @PostMapping("/schedule")
    EventIdResponse createEvent(LocalDateTime startTime, LocalDateTime deadline, String description, String title, int priority){
        if(!StpUtil.isLogin()){
            return new EventIdResponse(APIResponse.notLoggedIn(),-1);
        }
        return scheduleService.createEvent(StpUtil.getLoginIdAsInt(),startTime,deadline,description,title,priority);
    }

    @DeleteMapping("/schedule")
    APIResponse deleteEvent(int eventId){
        if(!StpUtil.isLogin()){
            return APIResponse.notLoggedIn();
        }
        return scheduleService.removeEvent(eventId);
    }

    @PatchMapping("/schedule")
    APIResponse updateEvent(int eventId,LocalDateTime startTime, LocalDateTime deadline, String description, String title, int priority){
        if(!StpUtil.isLogin()){
            return APIResponse.notLoggedIn();
        }
        return scheduleService.updateEventInfo(eventId,startTime,deadline,description,title,priority);
    }

    @GetMapping("/schedule")
    EventsResponse getEvents(){
        if(!StpUtil.isLogin()){
            return new EventsResponse(APIResponse.notLoggedIn(),null);
        }
        return scheduleService.getEvents(StpUtil.getLoginIdAsInt());
    }

    @GetMapping("/schedule/info")
    EventInfoResponse getEventInfo(int eventId){
        if(!StpUtil.isLogin()){
            return new EventInfoResponse(APIResponse.notLoggedIn());
        }
        return scheduleService.getEventInfo(eventId);
    }

    @GetMapping("/schedule/id")
    EventIdResponse getEventId(String title){
        if(!StpUtil.isLogin()){
            return new EventIdResponse(APIResponse.notLoggedIn(),-1);
        }
        return scheduleService.getEventId(StpUtil.getLoginIdAsInt(),title);
    }
}
