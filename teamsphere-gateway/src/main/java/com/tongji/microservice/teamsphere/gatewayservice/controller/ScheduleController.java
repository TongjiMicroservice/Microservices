package com.tongji.microservice.teamsphere.gatewayservice.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventIdResponse;
import com.tongji.microservice.teamsphere.dubbo.api.ScheduleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@Tag(name= "ScheduleController", description = "日程微服务接口")
public class ScheduleController {
    @DubboReference(check = false)
    private ScheduleService scheduleService;

    @PostMapping("/schedule/create")
    EventIdResponse createEvent(LocalDateTime startTime, LocalDateTime deadline, String description, String title, int priority){
        if(!StpUtil.isLogin()){
            return new EventIdResponse(APIResponse.notLoggedIn(),-1);
        }
        return scheduleService.createEvent(StpUtil.getLoginIdAsInt(),startTime,deadline,description,title,priority);
    }

}
