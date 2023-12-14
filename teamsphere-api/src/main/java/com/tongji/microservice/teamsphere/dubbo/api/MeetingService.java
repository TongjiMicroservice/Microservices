package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.entities.meetingservice.Meeting;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingService {
    String scheduleMeeting(String projectId, LocalDateTime meetingTime, List<String> participantIds, String agenda);
    void cancelMeeting(String meetingId);
    List<Meeting> getMeetingsForProject(String projectId);
    List<Meeting> getMeetingsForUser(String userId);
}
