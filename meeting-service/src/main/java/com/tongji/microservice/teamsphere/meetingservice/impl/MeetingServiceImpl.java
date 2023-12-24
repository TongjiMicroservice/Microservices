package com.tongji.microservice.teamsphere.meetingservice.impl;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.MeetingResponse;
import com.tongji.microservice.teamsphere.dubbo.api.MeetingService;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import com.tongji.microservice.teamsphere.meetingservice.mapper.MeetingMapper;
import com.tongji.microservice.teamsphere.meetingservice.mapper.MeetingParticipantsMapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@DubboService
public class MeetingServiceImpl implements MeetingService {


    @Autowired
    private MeetingMapper meetingMapper;

    @Autowired
    private MeetingParticipantsMapper meetingParticipantsMapper;

    @Override
    public APIResponse cancelMeeting(String meetingId){
        return new APIResponse();
    };

    @Override
    public APIResponse createMeeting(String projectId, String title, String description, LocalDateTime startTime, int duration){
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
