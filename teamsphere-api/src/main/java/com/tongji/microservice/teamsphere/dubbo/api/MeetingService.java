package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.MeetingResponse;
import com.tongji.microservice.teamsphere.entities.meetingservice.Meeting;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingService {
    APIResponse cancelMeeting(String meetingId);
    MeetingResponse getMeetingsForProject(String projectId);
    MeetingResponse getMeetingsForUser(String userId);
    APIResponse createMeeting(String projectId, String title, String description, LocalDateTime deadline);
}
