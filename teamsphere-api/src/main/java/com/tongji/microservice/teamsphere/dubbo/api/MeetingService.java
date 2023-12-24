package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.MeetingResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.MeetingListResponse;

import java.time.LocalDateTime;

public interface MeetingService {
    APIResponse cancelMeeting(String meetingId);
    MeetingListResponse getMeetingsForProject(String projectId);
    MeetingListResponse getMeetingsForUser(String userId);
    MeetingResponse createMeeting(String projectId, String title, String description, LocalDateTime starTime, LocalDateTime deadline);
}
