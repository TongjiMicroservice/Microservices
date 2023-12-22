package com.tongji.microservice.teamsphere.meetingservice.impl;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.MeetingResponse;
import com.tongji.microservice.teamsphere.dubbo.api.MeetingService;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class MeetingServiceImpl extends MeetingService {
    @Override
    public APIResponse cancelMeeting(String meetingId){
        return new APIResponse();
    };

    @Override
    public APIResponse createMeeting(String projectId, String title, String description, LocalDateTime deadline){
        return new APIResponse();
    };

    @Override
    public MeetingResponse getMeetingsForProject(String projectId){
        return null;
    };

    @Override
    public MeetingResponse getMeetingsForUser(String userId){
        return null;
    };
}
